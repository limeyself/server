package ac.limey.limeyac.predictionengine.movementtick;

import ac.limey.limeyac.player.LimeyPlayer;
import ac.limey.limeyac.predictionengine.predictions.PredictionEngineLava;
import ac.limey.limeyac.predictionengine.predictions.PredictionEngineNormal;
import ac.limey.limeyac.predictionengine.predictions.PredictionEngineWater;
import ac.limey.limeyac.predictionengine.predictions.PredictionEngineWaterLegacy;
import ac.limey.limeyac.utils.nmsutil.BlockProperties;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;

public class MovementTickerPlayer extends MovementTicker {
    public MovementTickerPlayer(LimeyPlayer player) {
        super(player);
    }

    @Override
    public void doWaterMove(float swimSpeed, boolean isFalling, float swimFriction) {
        if (player.getClientVersion().isNewerThanOrEquals(ClientVersion.V_1_13)) {
            new PredictionEngineWater().guessBestMovement(swimSpeed, player, isFalling, player.gravity, swimFriction);
        } else {
            new PredictionEngineWaterLegacy().guessBestMovement(swimSpeed, player, swimFriction);
        }
    }

    @Override
    public void doLavaMove() {
        new PredictionEngineLava().guessBestMovement(0.02F, player);
    }

    @Override
    public void doNormalMove(float blockFriction) {
        new PredictionEngineNormal().guessBestMovement(BlockProperties.getFrictionInfluencedSpeed(blockFriction, player), player);
    }
}
