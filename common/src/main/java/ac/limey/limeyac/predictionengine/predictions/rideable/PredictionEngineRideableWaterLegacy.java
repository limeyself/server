package ac.limey.limeyac.predictionengine.predictions.rideable;

import ac.limey.limeyac.player.LimeyPlayer;
import ac.limey.limeyac.predictionengine.predictions.PredictionEngineWaterLegacy;
import ac.limey.limeyac.predictionengine.predictions.input.Input;
import ac.limey.limeyac.utils.data.VectorData;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class PredictionEngineRideableWaterLegacy extends PredictionEngineWaterLegacy {
    private final Input movementVector;

    @Override
    public void addJumpsToPossibilities(LimeyPlayer player, Set<VectorData> existingVelocities) {
        PredictionEngineRideableUtils.handleJumps(player, existingVelocities);
    }

    @Override
    public List<VectorData> applyInputsToVelocityPossibilities(LimeyPlayer player, Set<VectorData> possibleVectors, float speed) {
        return PredictionEngineRideableUtils.applyInputsToVelocityPossibilities(movementVector, player, possibleVectors, speed);
    }
}
