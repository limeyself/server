package ac.limey.limeyac.checks.impl.badpackets;

import ac.grim.grimac.api.storage.verbose.Verbose;
import ac.limey.limeyac.checks.Check;
import ac.limey.limeyac.checks.CheckData;
import ac.limey.limeyac.checks.type.PacketReceiveListener;
import ac.limey.limeyac.player.LimeyPlayer;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerFlying;

@CheckData(name = "BadPacketsE", stableKey = "limey.badpackets.invalid_position", description = "Sent too many movement packets without updating position")
public class BadPacketsE extends Check implements PacketReceiveListener {
    private static final Verbose V = Verbose.of("ticks={uint}");

    private int noReminderTicks;
    private final int maxNoReminderTicks = player.getClientVersion().isOlderThanOrEquals(ClientVersion.V_1_8) ? 20 : 19;
    private final boolean isViaPleaseStopUsingProtocolHacksOnYourServer = player.getClientVersion().isNewerThanOrEquals(ClientVersion.V_1_21_2) || PacketEvents.getAPI().getServerManager().getVersion().isNewerThanOrEquals(ServerVersion.V_1_21_2);

    public BadPacketsE(LimeyPlayer player) {
        super(player);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.PLAYER_POSITION_AND_ROTATION ||
                event.getPacketType() == PacketType.Play.Client.PLAYER_POSITION) {
            noReminderTicks = 0;
        } else if (WrapperPlayClientPlayerFlying.isFlying(event.getPacketType()) && !player.packetStateData.lastPacketWasTeleport) {
            if (++noReminderTicks > maxNoReminderTicks) {
                flag(V.write(verbose()).uint(noReminderTicks));
            }
        } else if (event.getPacketType() == PacketType.Play.Client.STEER_VEHICLE
                || (isViaPleaseStopUsingProtocolHacksOnYourServer && player.inVehicle())) {
            noReminderTicks = 0; // Exempt vehicles
        }
    }

    public void handleRespawn() {
        noReminderTicks = 0;
    }
}
