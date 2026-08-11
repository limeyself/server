package ac.limey.limeyac.checks.impl.aim;

import ac.limey.limeyac.checks.Check;
import ac.limey.limeyac.checks.CheckData;
import ac.limey.limeyac.checks.type.RotationListener;
import ac.limey.limeyac.player.LimeyPlayer;
import ac.limey.limeyac.utils.anticheat.update.RotationUpdate;

@CheckData(name = "AimDuplicateLook", stableKey = "limey.aim.duplicate_look", description = "Sent a duplicate rotation update without changing look direction")
public class AimDuplicateLook extends Check implements RotationListener {
    private boolean exempt;

    public AimDuplicateLook(LimeyPlayer playerData) {
        super(playerData);
    }

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        if (player.packetStateData.lastPacketWasTeleport || player.packetStateData.lastPacketWasOnePointSeventeenDuplicate || player.compensatedEntities.self.getRiding() != null) {
            exempt = true;
            return;
        }

        if (exempt) { // Exempt for a tick on teleport
            exempt = false;
            return;
        }

        if (rotationUpdate.getFrom().equals(rotationUpdate.getTo())) {
            flag();
        }
    }
}
