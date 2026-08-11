package ac.limey.limeyac.checks.impl.vehicle;

import ac.grim.grimac.api.storage.verbose.Verbose;
import ac.limey.limeyac.checks.Check;
import ac.limey.limeyac.checks.CheckData;
import ac.limey.limeyac.checks.impl.verbose.VerboseCodecs;
import ac.limey.limeyac.checks.type.PacketReceiveListener;
import ac.limey.limeyac.player.LimeyPlayer;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEntityAction;

@CheckData(name = "VehicleD", stableKey = "limey.vehicle.spoofed_jump", experimental = true, description = "Jumped in a vehicle that cannot jump")
public class VehicleD extends Check implements PacketReceiveListener {
    private static final Verbose V = Verbose.of("vehicle=[{entity}|null]");

    public VehicleD(LimeyPlayer player) {
        super(player);
    }

    @Override
    public void onPacketReceive(final PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.ENTITY_ACTION && new WrapperPlayClientEntityAction(event).getAction() == WrapperPlayClientEntityAction.Action.START_JUMPING_WITH_HORSE) {
            final EntityType vehicle = player.getVehicleType();

            if (!EntityTypes.isTypeInstanceOf(vehicle, EntityTypes.ABSTRACT_HORSE) && !EntityTypes.isTypeInstanceOf(vehicle, EntityTypes.ABSTRACT_NAUTILUS)) {
                if (flag(V.write(verbose()).bool(vehicle != null).uint(vehicle == null ? 0 : VerboseCodecs.entity(vehicle, player.getClientVersion()))) && shouldModifyPackets()) {
                    event.setCancelled(true);
                    player.onPacketCancel();
                }
            }
        }
    }
}
