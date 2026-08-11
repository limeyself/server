package ac.limey.limeyac.predictionengine.predictions.input;

import ac.limey.limeyac.player.LimeyPlayer;
import ac.limey.limeyac.utils.math.Vector3dm;
import ac.limey.limeyac.utils.math.VectorUtils;

public record DoubleInput(double sideways, double vertical, double forward) implements Input {
    @Override
    public Vector3dm vector() {
        return new Vector3dm(sideways, vertical, forward);
    }

    @Override
    public Input normalize(LimeyPlayer player) {
        double lengthSquared = sideways * sideways + vertical * vertical + forward * forward;
        if (lengthSquared > 1) {
            double d0 = VectorUtils.getVanillaLength(player.getClientVersion(), sideways, vertical, forward);
            return new DoubleInput(sideways / d0, vertical / d0, forward / d0);
        }

        return this;
    }
}
