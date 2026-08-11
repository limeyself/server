package ac.limey.limeyac.checks.impl.movement;

import ac.limey.limeyac.checks.Check;
import ac.limey.limeyac.checks.type.PositionListener;
import ac.limey.limeyac.player.LimeyPlayer;
import ac.limey.limeyac.utils.anticheat.update.PositionUpdate;

public class PredictionRunner extends Check implements PositionListener {
    public PredictionRunner(LimeyPlayer playerData) {
        super(playerData);
    }

    @Override
    public void onPositionUpdate(final PositionUpdate positionUpdate) {
        if (!player.inVehicle()) {
            player.movementCheckRunner.processAndCheckMovementPacket(positionUpdate);
        }
    }
}
