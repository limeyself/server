package ac.limey.limeyac.checks.impl.sprint;

import ac.grim.grimac.api.storage.verbose.Verbose;
import ac.limey.limeyac.checks.Check;
import ac.limey.limeyac.checks.CheckData;
import ac.limey.limeyac.checks.type.PostPredictionListener;
import ac.limey.limeyac.player.LimeyPlayer;
import ac.limey.limeyac.utils.anticheat.update.PredictionComplete;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;

@CheckData(name = "SprintA", stableKey = "limey.sprint.hunger", description = "Sprinting with too low hunger", setback = 0)
public class SprintA extends Check implements PostPredictionListener {
    private static final Verbose V = Verbose.of("hunger={uint}");

    public SprintA(LimeyPlayer player) {
        super(player);
    }

    @Override
    public void onPredictionComplete(PredictionComplete predictionComplete) {
        if (!predictionComplete.isChecked()) return;

        // Players can sprint if they're able to fly
        // Players can also sprint if they are on a camel, regardless of their hunger level
        if (player.canFly || EntityTypes.isTypeInstanceOf(player.getVehicleType(), EntityTypes.CAMEL)) return;

        if (player.food <= 6.0F) {
            if (player.isSprinting) {
                flagWithSetback(V.write(verbose()).uint(player.food));
            } else {
                reward();
            }
        }
    }
}
