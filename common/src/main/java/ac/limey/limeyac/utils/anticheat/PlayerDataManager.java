package ac.limey.limeyac.utils.anticheat;

import ac.limey.limeyac.LimeyAPI;
import ac.grim.grimac.api.event.events.GrimJoinEvent;
import ac.grim.grimac.api.event.events.GrimQuitEvent;
import ac.limey.limeyac.player.LimeyPlayer;
import ac.limey.limeyac.platform.api.player.PlatformPlayer;
import ac.limey.limeyac.platform.api.player.PlatformPlayerCache;
import ac.limey.limeyac.utils.reflection.GeyserUtil;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.netty.channel.ChannelHelper;
import com.github.retrooper.packetevents.protocol.player.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerDataManager {

    // Holder — PlayerDataManager is constructed inside LimeyAPI's ctor, so a
    // plain static-final would see a null LimeyAPI.INSTANCE. Holder init runs
    // on first fire, after LimeyAPI is fully built.
    private static final class Channels {
        static final GrimJoinEvent.Channel JOIN = LimeyAPI.INSTANCE.getEventBus().get(GrimJoinEvent.class);
        static final GrimQuitEvent.Channel QUIT = LimeyAPI.INSTANCE.getEventBus().get(GrimQuitEvent.class);
    }

    private final Set<User> exemptUsers = ConcurrentHashMap.newKeySet();
    private final ConcurrentHashMap<User, LimeyPlayer> playerDataMap = new ConcurrentHashMap<>();

    public boolean isExemptUser(@Nullable User user) {
        return user != null && exemptUsers.contains(user);
    }

    public void exemptUser(@Nullable User user) {
        if (user == null) return;
        exemptUsers.add(user);
    }

    public boolean clearExemptions(@Nullable User user) {
        if (user == null) return false;
        return exemptUsers.remove(user);
    }

    @Nullable
    public LimeyPlayer getPlayer(final @NotNull UUID uuid) {
        // Is it safe to interact with this, or is this internal PacketEvents code?
        Object channel = PacketEvents.getAPI().getProtocolManager().getChannel(uuid);
        if (channel == null) return null;
        User user = PacketEvents.getAPI().getProtocolManager().getUser(channel);
        if (user == null) return null;
        return getPlayer(user);
    }

    @Nullable
    public LimeyPlayer getPlayer(final @NotNull User user) {
        @Nullable LimeyPlayer player = playerDataMap.get(user);
        if (player != null && player.platformPlayer != null && player.platformPlayer.isExternalPlayer())
            return null;
        return player;
    }

    public boolean shouldCheck(@NotNull User user) {
        if (isExemptUser(user)) return false;
        if (!ChannelHelper.isOpen(user.getChannel())) return false;

        if (user.getUUID() != null) {
            // Bedrock players don't have Java movement
            if (GeyserUtil.isBedrockPlayer(user.getUUID())) {
                exemptUser(user);
                return false;
            }

            // Has exempt permission
            LimeyPlayer limeyPlayer = LimeyAPI.INSTANCE.getPlayerDataManager().getPlayer(user);
            if (limeyPlayer != null && limeyPlayer.hasPermission("limey.exempt")) {
                exemptUser(user);
                return false;
            }

            // Geyser formatted player string
            // This will never happen for Java players, as the first character in the 3rd group is always 4 (xxxxxxxx-xxxx-4xxx-xxxx-xxxxxxxxxxxx)
            if (user.getUUID().toString().startsWith("00000000-0000-0000-0009")) {
                exemptUser(user);
                return false;
            }
        }

        return true;
    }

    public void addUser(final @NotNull User user) {
        if (shouldCheck(user)) {
            LimeyPlayer player = new LimeyPlayer(user);
            playerDataMap.put(user, player);
            Channels.JOIN.fire(player);
        }
    }

    public LimeyPlayer remove(final @NotNull User user) {
        return playerDataMap.remove(user);
    }

    public void onDisconnect(User user) {
        LimeyPlayer limeyPlayer = remove(user);
        if (limeyPlayer != null) Channels.QUIT.fire(limeyPlayer);
        clearExemptions(user);

        UUID uuid = user.getProfile().getUUID();

        // All cleanup paths should call onDisconnect; routing the session-close + toggle
        // eviction here means a stuck PE event (or a JVM-level channel
        // close that doesn't surface as UserDisconnectEvent) doesn't leak an open session.
        // hooks/toggles are NOOP when the datastore is disabled or its init failed
        // AND go NOOP mid-session if an operator runs /limey reload after flipping database.enabled to false
        // a player who joined under the prior (enabled) config and disconnects post-reload has no live writer to fire onQuit, so their session stays open (row closed_at IS NULL).
        // The next datastore-enabled boot's crash sweep stamps closed_at = last_activity for still-open rows; permanently-disabled-after-the-fact leaves the row untouched until DB is enabled again.
        LimeyAPI.INSTANCE.getDataStoreLifecycle().liveWriteHooks()
                .onQuitFromUserDisconnect(user, limeyPlayer, System.currentTimeMillis());
        if (uuid != null) {
            LimeyAPI.INSTANCE.getDataStoreLifecycle().playerToggleStore().evict(uuid);
        }

        // Check if calling async is safe
        if (uuid == null)
            return; // folia doesn't like null getPlayer()

        PlatformPlayer quittingPlayer = PlatformPlayerCache.getInstance().getPlayer(uuid);
        if (quittingPlayer != null) {
            LimeyAPI.INSTANCE.getAlertManager().handlePlayerQuit(quittingPlayer);
        }

        LimeyAPI.INSTANCE.getSpectateManager().onQuit(uuid);

        // TODO (Cross-platform) confirm this is 100% correct and will always remove players from cache when necessary
        LimeyAPI.INSTANCE.getPlatformPlayerFactory().invalidatePlayer(uuid);
    }

    public Collection<LimeyPlayer> getEntries() {
        return playerDataMap.values();
    }

    public int size() {
        return playerDataMap.size();
    }
}
