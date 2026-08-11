package ac.limey.limeyac.checks.impl.combat;

import ac.limey.limeyac.checks.Check;
import ac.limey.limeyac.checks.CheckData;
import ac.limey.limeyac.checks.type.PreViaPacketReceiveListener;
import ac.limey.limeyac.player.LimeyPlayer;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientAttack;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientSpectateEntity;

@CheckData(name = "SelfInteract", stableKey = "limey.badpackets.self_hit", description = "Interacted with self")
public class SelfInteract extends Check implements PreViaPacketReceiveListener {
    public SelfInteract(LimeyPlayer player) {
        super(player);
    }

    @Override
    public void onPreViaPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.INTERACT_ENTITY) {
            WrapperPlayClientInteractEntity packet = new WrapperPlayClientInteractEntity(event);
            onInteract(event, packet.getEntityId());
        }

        if (event.getPacketType() == PacketType.Play.Client.ATTACK) {
            WrapperPlayClientAttack packet = new WrapperPlayClientAttack(event);
            onInteract(event, packet.getEntityId());
        }

        if (event.getPacketType() == PacketType.Play.Client.SPECTATE_ENTITY) {
            WrapperPlayClientSpectateEntity packet = new WrapperPlayClientSpectateEntity(event);
            onInteract(event, packet.getEntityId());
        }
    }

    // TODO: should check for camera entity id instead of player entity id?
    private void onInteract(PacketReceiveEvent event, int entityId) {
        if (player.cameraEntity.isSelf() && entityId == player.entityID
                && flag() && shouldModifyPackets()) { // Instant ban
            event.setCancelled(true);
            player.onPacketCancel();
        }
    }
}
