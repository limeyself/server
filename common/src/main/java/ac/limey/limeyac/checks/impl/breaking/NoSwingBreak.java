package ac.limey.limeyac.checks.impl.breaking;

import ac.limey.limeyac.checks.Check;
import ac.limey.limeyac.checks.CheckData;
import ac.limey.limeyac.checks.type.BlockBreakListener;
import ac.limey.limeyac.checks.type.PreViaPacketReceiveListener;
import ac.limey.limeyac.player.LimeyPlayer;
import ac.limey.limeyac.utils.anticheat.update.BlockBreak;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.DiggingAction;

@CheckData(name = "NoSwingBreak", stableKey = "limey.breaking.no_swing_break", description = "Did not swing while breaking block", experimental = true)
public class NoSwingBreak extends Check implements BlockBreakListener, PreViaPacketReceiveListener {
    private boolean sentAnimation;
    private boolean sentBreak;

    public NoSwingBreak(LimeyPlayer playerData) {
        super(playerData);
    }

    @Override
    public void onBlockBreak(BlockBreak blockBreak) {
        if (blockBreak.action != DiggingAction.CANCELLED_DIGGING) {
            sentBreak = true;
        }
    }

    @Override
    public void onPreViaPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.ANIMATION) {
            sentAnimation = true;
        }

        if (isTickPacket(event.getPacketType())) {
            if (sentBreak && !sentAnimation) {
                flag();
            }

            sentAnimation = sentBreak = false;
        }
    }
}
