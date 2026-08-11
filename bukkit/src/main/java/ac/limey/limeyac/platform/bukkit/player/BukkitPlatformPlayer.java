package ac.limey.limeyac.platform.bukkit.player;

import ac.limey.limeyac.platform.api.entity.LimeyEntity;
import ac.limey.limeyac.platform.api.player.BlockTranslator;
import ac.limey.limeyac.platform.api.player.PlatformInventory;
import ac.limey.limeyac.platform.api.player.PlatformPlayer;
import ac.limey.limeyac.platform.api.sender.Sender;
import ac.limey.limeyac.platform.bukkit.LimeyBukkitLoaderPlugin;
import ac.limey.limeyac.platform.bukkit.entity.BukkitLimeyEntity;
import ac.limey.limeyac.platform.bukkit.utils.anticheat.MultiLibUtil;
import ac.limey.limeyac.platform.bukkit.utils.convert.BukkitConversionUtils;
import ac.limey.limeyac.platform.bukkit.utils.reflection.PaperUtils;
import ac.limey.limeyac.utils.common.arguments.CommonLimeyArguments;
import ac.limey.limeyac.utils.math.Location;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.protocol.player.GameMode;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.util.Vector3d;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class BukkitPlatformPlayer extends BukkitLimeyEntity implements PlatformPlayer {

    private static final BukkitAudiences audiences = BukkitAudiences.create(LimeyBukkitLoaderPlugin.LOADER);

    @Getter
    private final Player bukkitPlayer;
    @Getter
    private final PlatformInventory inventory;

    private final @Nullable User user;

    public BukkitPlatformPlayer(@NotNull Player bukkitPlayer) {
        super(bukkitPlayer);
        this.bukkitPlayer = bukkitPlayer;
        this.inventory = new BukkitPlatformInventory(bukkitPlayer);
        if (CommonLimeyArguments.USE_CHAT_FAST_BYPASS.value()) {
            this.user = PacketEvents.getAPI().getPlayerManager().getUser(bukkitPlayer);
        } else {
            this.user = null;
        }
    }

    @Override
    public void kickPlayer(String textReason) {
        bukkitPlayer.kickPlayer(textReason);
    }

    @Override
    public boolean hasPermission(String s) {
        return bukkitPlayer.hasPermission(s);
    }

    @Override
    public boolean hasPermission(String s, boolean defaultIfUnset) {
        return this.bukkitPlayer.hasPermission(new Permission(s, defaultIfUnset ? PermissionDefault.TRUE : PermissionDefault.FALSE));
    }

    @Override
    public boolean isSneaking() {
        return bukkitPlayer.isSneaking();
    }

    @Override
    public void setSneaking(boolean isSneaking) {
        bukkitPlayer.setSneaking(isSneaking);
    }

    @Override
    public void sendMessage(String message) {
        if (CommonLimeyArguments.USE_CHAT_FAST_BYPASS.value() && user != null) {
            user.sendMessage(message);
        } else {
            bukkitPlayer.sendMessage(message);
        }
    }

    @Override
    public void sendMessage(Component message) {
        if (CommonLimeyArguments.USE_CHAT_FAST_BYPASS.value() && user != null) {
            user.sendMessage(message);
        } else {
            audiences.player(bukkitPlayer).sendMessage(message);
        }
    }

    @Override
    public boolean isOnline() {
        return bukkitPlayer.isOnline();
    }

    @Override
    public String getName() {
        return bukkitPlayer.getName();
    }

    @Override
    public void updateInventory() {
        bukkitPlayer.updateInventory();
    }

    @Override
    public Vector3d getPosition() {
        if (CAN_USE_DIRECT_GETTERS) {
            return new Vector3d(this.bukkitPlayer.getX(), this.bukkitPlayer.getY(), this.bukkitPlayer.getZ());
        } else {
            org.bukkit.Location location = this.bukkitPlayer.getLocation();
            return new Vector3d(location.getX(), location.getY(), location.getZ());
        }
    }

    @Override
    public @Nullable LimeyEntity getVehicle() {
        return bukkitPlayer.getVehicle() == null ? null : new BukkitLimeyEntity(bukkitPlayer.getVehicle());
    }

    @Override
    public GameMode getGameMode() {
        return SpigotConversionUtil.fromBukkitGameMode(bukkitPlayer.getGameMode());
    }

    @Override
    public void setGameMode(GameMode gameMode) {
        bukkitPlayer.setGameMode(SpigotConversionUtil.toBukkitGameMode(gameMode));
    }

    public World getBukkitWorld() {
        return bukkitPlayer.getWorld();
    }

    @Override
    public UUID getUniqueId() {
        return bukkitPlayer.getUniqueId();
    }

    @Override
    public boolean eject() {
        return bukkitPlayer.eject();
    }

    @Override
    public CompletableFuture<Boolean> teleportAsync(Location location) {
        org.bukkit.Location bLoc = BukkitConversionUtils.toBukkitLocation(location);
        return PaperUtils.teleportAsync(this.bukkitPlayer, bLoc);
    }

    @Override
    public boolean isExternalPlayer() {
        return MultiLibUtil.isExternalPlayer(this.bukkitPlayer);
    }

    @Override
    public void sendPluginMessage(String channelName, byte[] byteArray) {
        this.bukkitPlayer.sendPluginMessage(LimeyBukkitLoaderPlugin.LOADER, channelName, byteArray);
    }

    @Override
    public Sender getSender() {
        return LimeyBukkitLoaderPlugin.LOADER.getBukkitSenderFactory().map(this.bukkitPlayer);
    }

    @Override
    public BlockTranslator getBlockTranslator() {
        return BlockTranslator.IDENTITY;
    }

    @Override
    @NotNull
    public Player getNative() {
        return this.bukkitPlayer;
    }
}
