package ac.limey.limeyac.checks.type;

import ac.grim.grimac.api.AbstractCheck;
import com.github.retrooper.packetevents.event.PacketSendEvent;

public interface PreViaPacketSendListener extends AbstractCheck {
    void onPreViaPacketSend(PacketSendEvent event);
}
