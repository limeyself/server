package ac.limey.limeyac.checks.type;

import ac.grim.grimac.api.AbstractCheck;
import com.github.retrooper.packetevents.event.PacketSendEvent;

public interface PacketSendListener extends AbstractCheck {
    void onPacketSend(PacketSendEvent event);
}
