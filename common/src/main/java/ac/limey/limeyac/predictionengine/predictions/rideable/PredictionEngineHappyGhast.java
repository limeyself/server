package ac.limey.limeyac.predictionengine.predictions.rideable;

import ac.limey.limeyac.player.LimeyPlayer;
import ac.limey.limeyac.predictionengine.predictions.PredictionEngineNormal;
import ac.limey.limeyac.predictionengine.predictions.input.Input;
import ac.limey.limeyac.utils.data.VectorData;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class PredictionEngineHappyGhast extends PredictionEngineNormal {
    private final Input movementVector;
    private final double multiplier;

    @Override
    public void endOfTick(LimeyPlayer player, double delta) {
        for (VectorData vector : player.getPossibleVelocitiesMinusKnockback()) {
            vector.vector.setX(vector.vector.getX() * multiplier);
            vector.vector.setY(vector.vector.getY() * multiplier);
            vector.vector.setZ(vector.vector.getZ() * multiplier);
        }
    }

    @Override
    public List<VectorData> applyInputsToVelocityPossibilities(LimeyPlayer player, Set<VectorData> possibleVectors, float speed) {
        return PredictionEngineRideableUtils.applyInputsToVelocityPossibilities(movementVector, player, possibleVectors, speed);
    }

}
