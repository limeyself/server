package ac.limey.limeyac.checks.impl.aim.processor;

import ac.limey.limeyac.checks.Check;
import ac.limey.limeyac.checks.type.RotationListener;
import ac.limey.limeyac.player.LimeyPlayer;
import ac.limey.limeyac.utils.anticheat.update.RotationUpdate;
import ac.limey.limeyac.utils.data.Pair;
import ac.limey.limeyac.utils.lists.RunningMode;
import ac.limey.limeyac.utils.math.LimeyMath;

public class AimProcessor extends Check implements RotationListener {

    private static final int SIGNIFICANT_SAMPLES_THRESHOLD = 15;
    private static final int TOTAL_SAMPLES_THRESHOLD = 80;
    public double sensitivityX;
    public double sensitivityY;
    public double divisorX;
    public double divisorY;
    public double modeX, modeY;
    public double deltaDotsX, deltaDotsY;
    private final RunningMode xRotMode = new RunningMode(TOTAL_SAMPLES_THRESHOLD);
    private final RunningMode yRotMode = new RunningMode(TOTAL_SAMPLES_THRESHOLD);
    private float lastXRot;
    private float lastYRot;

    public AimProcessor(LimeyPlayer playerData) {
        super(playerData);
    }

    public static double convertToSensitivity(double var13) {
        double var11 = var13 / 0.15F / 8.0D;
        double var9 = Math.cbrt(var11);
        return (var9 - 0.2f) / 0.6f;
    }

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        rotationUpdate.setProcessor(this);

        float deltaXRot = rotationUpdate.getDeltaXRotABS();

        this.divisorX = LimeyMath.gcd(deltaXRot, lastXRot);
        if (deltaXRot > 0 && deltaXRot < 5 && divisorX > LimeyMath.MINIMUM_DIVISOR) {
            this.xRotMode.add(divisorX);
            this.lastXRot = deltaXRot;
        }

        float deltaYRot = rotationUpdate.getDeltaYRotABS();

        this.divisorY = LimeyMath.gcd(deltaYRot, lastYRot);

        if (deltaYRot > 0 && deltaYRot < 5 && divisorY > LimeyMath.MINIMUM_DIVISOR) {
            this.yRotMode.add(divisorY);
            this.lastYRot = deltaYRot;
        }

        if (this.xRotMode.size() > SIGNIFICANT_SAMPLES_THRESHOLD) {
            Pair<Double, Integer> modeX = this.xRotMode.getMode();
            if (modeX.second() > SIGNIFICANT_SAMPLES_THRESHOLD) {
                this.modeX = modeX.first();
                this.sensitivityX = convertToSensitivity(this.modeX);
            }
        }
        if (this.yRotMode.size() > SIGNIFICANT_SAMPLES_THRESHOLD) {
            Pair<Double, Integer> modeY = this.yRotMode.getMode();
            if (modeY.second() > SIGNIFICANT_SAMPLES_THRESHOLD) {
                this.modeY = modeY.first();
                this.sensitivityY = convertToSensitivity(this.modeY);
            }
        }

        this.deltaDotsX = deltaXRot / modeX;
        this.deltaDotsY = deltaYRot / modeY;
    }
}
