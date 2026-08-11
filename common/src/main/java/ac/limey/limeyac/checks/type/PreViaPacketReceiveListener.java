package ac.limey.limeyac.checks.type;

import ac.grim.grimac.api.AbstractCheck;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;

public interface PreViaPacketReceiveListener extends AbstractCheck {
    void onPreViaPacketReceive(PacketReceiveEvent event);
}
