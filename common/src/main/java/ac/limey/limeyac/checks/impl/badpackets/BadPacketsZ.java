package ac.limey.limeyac.checks.impl.badpackets;

import ac.limey.limeyac.checks.Check;
import ac.limey.limeyac.checks.CheckData;
import ac.limey.limeyac.checks.type.PreViaPacketReceiveListener;
import ac.limey.limeyac.player.LimeyPlayer;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;

@CheckData(name = "BadPacketsZ", stableKey = "limey.badpackets.duplicate_player_input", description = "Sent duplicate player input packets in the same client tick", experimental = true)
public class BadPacketsZ extends Check implements PreViaPacketReceiveListener {
    private boolean sent;

    public BadPacketsZ(LimeyPlayer player) {
        super(player);
    }

    @Override
    public void onPreViaPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.CLIENT_TICK_END) {
            sent = false;
        }

        if (event.getPacketType() == PacketType.Play.Client.PLAYER_INPUT) {
            if (sent) {
                flag();
            }

            sent = true;
        }
    }
}
