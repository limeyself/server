package ac.limey.limeyac.manager;

import ac.limey.limeyac.checks.Check;
import ac.limey.limeyac.checks.type.PostPredictionListener;
import ac.limey.limeyac.player.LimeyPlayer;
import ac.limey.limeyac.utils.anticheat.update.PredictionComplete;
import ac.limey.limeyac.utils.data.LastInstance;

import java.util.ArrayList;
import java.util.List;

public class LastInstanceManager extends Check implements PostPredictionListener {
    private final List<LastInstance> instances = new ArrayList<>();

    public LastInstanceManager(LimeyPlayer player) {
        super(player);
    }

    public void addInstance(LastInstance instance) {
        instances.add(instance);
    }

    @Override
    public void onPredictionComplete(final PredictionComplete predictionComplete) {
        for (LastInstance instance : instances) {
            instance.tick();
        }
    }
}
