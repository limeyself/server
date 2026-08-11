package ac.limey.limeyac.checks.impl.baritone;

import ac.grim.grimac.api.storage.verbose.Verbose;
import ac.limey.limeyac.checks.Check;
import ac.limey.limeyac.checks.CheckData;
import ac.limey.limeyac.checks.impl.aim.processor.AimProcessor;
import ac.limey.limeyac.checks.type.RotationListener;
import ac.limey.limeyac.player.LimeyPlayer;
import ac.limey.limeyac.utils.anticheat.update.RotationUpdate;
import ac.limey.limeyac.utils.data.HeadRotation;
import ac.limey.limeyac.utils.math.LimeyMath;

// This check has been patched by Baritone for a long time, and it also seems to false with cinematic camera now, so it is disabled.
@CheckData(name = "Baritone", stableKey = "limey.baritone.baritone", description = "Detected Baritone like behavior")
public class Baritone extends Check implements RotationListener {
    private static final Verbose V = Verbose.of("divisor={f64}");

    private int verbose;

    public Baritone(LimeyPlayer playerData) {
        super(playerData);
    }

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        final HeadRotation from = rotationUpdate.getFrom();
        final HeadRotation to = rotationUpdate.getTo();

        final float deltaPitch = Math.abs(to.pitch() - from.pitch());

        // Baritone works with small degrees, limit to 1 degree to pick up on baritone slightly moving aim to bypass anticheats
        if (rotationUpdate.getDeltaXRot() == 0 && deltaPitch > 0 && deltaPitch < 1 && Math.abs(to.pitch()) != 90.0f) {
            if (rotationUpdate.getProcessor().divisorY < LimeyMath.MINIMUM_DIVISOR) {
                verbose++;
                if (verbose > 8) {
                    double divisor = AimProcessor.convertToSensitivity(rotationUpdate.getProcessor().divisorX);
                    flag(V.write(verbose()).f64(divisor));
                }
            } else {
                verbose = 0;
            }
        }
    }
}
