package ac.limey.limeyac.checks.impl.sprint;

import ac.limey.limeyac.checks.Check;
import ac.limey.limeyac.checks.CheckData;
import ac.limey.limeyac.checks.type.PostPredictionListener;
import ac.limey.limeyac.player.LimeyPlayer;
import ac.limey.limeyac.utils.anticheat.update.PredictionComplete;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;

@CheckData(name = "SprintG", stableKey = "limey.sprint.water", description = "Sprinting while in water", experimental = true)
public class SprintG extends Check implements PostPredictionListener {
    public SprintG(LimeyPlayer player) {
        super(player);
    }

    @Override
    public void onPredictionComplete(final PredictionComplete predictionComplete) {
        if (player.wasTouchingWater && (player.wasWasTouchingWater || player.getClientVersion() == ClientVersion.V_1_21_4)
                && !player.wasEyeInWater && player.getClientVersion().isNewerThanOrEquals(ClientVersion.V_1_13)
                && player.wasLastPredictionCompleteChecked && predictionComplete.isChecked()
                && !EntityTypes.isTypeInstanceOf(player.getVehicleType(), EntityTypes.CAMEL)
                && !player.isSwimming) {
            if (player.isSprinting) {
                flagWithSetback();
            } else {
                reward();
            }
        }
    }
}
