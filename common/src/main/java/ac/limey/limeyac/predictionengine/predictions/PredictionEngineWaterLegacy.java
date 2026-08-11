package ac.limey.limeyac.predictionengine.predictions;

import ac.limey.limeyac.player.LimeyPlayer;
import ac.limey.limeyac.utils.data.VectorData;

import java.util.HashSet;
import java.util.Set;

public class PredictionEngineWaterLegacy extends PredictionEngine {

    private float swimmingFriction;

    public void guessBestMovement(float swimmingSpeed, LimeyPlayer player, float swimmingFriction) {
        this.swimmingFriction = swimmingFriction;
        super.guessBestMovement(swimmingSpeed, player);
    }

    @Override
    public void addJumpsToPossibilities(LimeyPlayer player, Set<VectorData> existingVelocities) {
        for (VectorData vector : new HashSet<>(existingVelocities)) {
            existingVelocities.add(new VectorData(vector.vector.clone().add(0, 0.04f, 0), vector, VectorData.VectorType.Jump));

            if (player.skippedTickInActualMovement) {
                existingVelocities.add(new VectorData(vector.vector.clone().add(0, 0.02f, 0), vector, VectorData.VectorType.Jump));
            }
        }
    }

    @Override
    public void endOfTick(LimeyPlayer player, double playerGravity) {
        super.endOfTick(player, playerGravity);

        for (VectorData vector : player.getPossibleVelocitiesMinusKnockback()) {
            vector.vector.multiply(swimmingFriction, 0.8F, swimmingFriction);

            // Gravity
            vector.vector.setY(vector.vector.getY() - 0.02D);
        }
    }
}
