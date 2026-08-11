package ac.limey.limeyac.checks.impl.multiactions;

import ac.limey.limeyac.checks.Check;
import ac.limey.limeyac.checks.CheckData;
import ac.limey.limeyac.checks.type.PreViaPacketReceiveListener;
import ac.limey.limeyac.player.LimeyPlayer;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.DiggingAction;
import com.github.retrooper.packetevents.protocol.player.InteractionHand;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerDigging;

@CheckData(name = "MultiActionsA", stableKey = "limey.multiactions.attack_while_using", description = "Attacked while using an item", experimental = true)
public class MultiActionsA extends Check implements PreViaPacketReceiveListener {
    public MultiActionsA(LimeyPlayer player) {
        super(player);
    }

    @Override
    public void onPreViaPacketReceive(PacketReceiveEvent event) {
        if (player.packetStateData.isSlowedByUsingItem() && (player.packetStateData.lastSlotSelected == player.packetStateData.getSlowedByUsingItemSlot() || player.packetStateData.itemInUseHand == InteractionHand.OFF_HAND)) {
            if (event.getPacketType() == PacketType.Play.Client.INTERACT_ENTITY && new WrapperPlayClientInteractEntity(event).getAction() == WrapperPlayClientInteractEntity.InteractAction.ATTACK
                    || event.getPacketType() == PacketType.Play.Client.ATTACK || event.getPacketType() == PacketType.Play.Client.SPECTATE_ENTITY
                    || event.getPacketType() == PacketType.Play.Client.PLAYER_DIGGING && new WrapperPlayClientPlayerDigging(event).getAction() == DiggingAction.STAB) {
                if (flag() && shouldModifyPackets()) {
                    event.setCancelled(true);
                    player.onPacketCancel();
                }
            }
        }
    }
}
