package ac.limey.limeyac.checks.type;

import ac.grim.grimac.api.AbstractCheck;
import ac.limey.limeyac.utils.anticheat.update.PredictionComplete;

public interface PostPredictionListener extends AbstractCheck {
    void onPredictionComplete(PredictionComplete predictionComplete);
}
