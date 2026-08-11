package ac.limey.limeyac.checks.impl.chat;

import ac.limey.limeyac.checks.Check;
import ac.limey.limeyac.checks.CheckData;
import ac.limey.limeyac.checks.type.PreViaPacketReceiveListener;
import ac.limey.limeyac.player.LimeyPlayer;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientTabComplete;

@CheckData(name = "ChatA", stableKey = "limey.exploit.blank_tab_complete", description = "Sent a tab complete packet with no command or input text", experimental = true)
public class ChatA extends Check implements PreViaPacketReceiveListener {

    public ChatA(LimeyPlayer player) {
        super(player);
    }

    @Override
    public boolean isApplicable() {
        return PacketEvents.getAPI().getServerManager().getVersion().isNewerThanOrEquals(ServerVersion.V_1_13);
    }

    @Override
    public void onPreViaPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.TAB_COMPLETE) {
            WrapperPlayClientTabComplete wrapper = new WrapperPlayClientTabComplete(event);
            String text = wrapper.getText();
            if (text.equals("/") || text.trim().isEmpty()) {
                if (flag() && shouldModifyPackets()) {
                    event.setCancelled(true);
                    player.onPacketCancel();
                }
            }
        }
    }
}
