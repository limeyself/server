package ac.limey.limeyac.checks.impl.badpackets;

import ac.limey.limeyac.checks.Check;
import ac.limey.limeyac.checks.CheckData;
import ac.limey.limeyac.checks.type.PreViaPacketReceiveListener;
import ac.limey.limeyac.player.LimeyPlayer;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.GameMode;

@CheckData(name = "BadPacketsK", stableKey = "limey.badpackets.invalid_spectate", description = "Sent spectate packets while not in spectator mode")
public class BadPacketsK extends Check implements PreViaPacketReceiveListener {
    public BadPacketsK(LimeyPlayer player) {
        super(player);
    }

    @Override
    public void onPreViaPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.SPECTATE
                && player.gamemode != GameMode.SPECTATOR
                && flag() && shouldModifyPackets()) {
            event.setCancelled(true);
            player.onPacketCancel();
        }
    }
}
