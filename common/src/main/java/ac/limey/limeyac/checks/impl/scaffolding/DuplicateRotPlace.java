package ac.limey.limeyac.checks.impl.scaffolding;

import ac.grim.grimac.api.storage.verbose.Verbose;
import ac.limey.limeyac.checks.CheckData;
import ac.limey.limeyac.checks.type.BlockPlaceCheck;
import ac.limey.limeyac.checks.type.PostFlyingBlockPlaceListener;
import ac.limey.limeyac.checks.type.RotationListener;
import ac.limey.limeyac.player.LimeyPlayer;
import ac.limey.limeyac.utils.anticheat.update.BlockPlace;
import ac.limey.limeyac.utils.anticheat.update.RotationUpdate;

@CheckData(name = "DuplicateRotPlace", stableKey = "limey.scaffolding.duplicate_rot_place", description = "Repeated the same rotation delta while placing blocks", experimental = true)
public class DuplicateRotPlace extends BlockPlaceCheck implements RotationListener, PostFlyingBlockPlaceListener {
    private static final Verbose V = Verbose.of("x={f64} xdots={f64} y={f64}");

    private float deltaX, deltaY;
    private float lastPlacedDeltaX;
    private double lastPlacedDeltaDotsX;
    private double deltaDotsX;
    private boolean rotated = false;

    public DuplicateRotPlace(LimeyPlayer player) {
        super(player);
    }

    @Override
    public void process(final RotationUpdate rotationUpdate) {
        deltaX = rotationUpdate.getDeltaXRotABS();
        deltaY = rotationUpdate.getDeltaYRotABS();
        deltaDotsX = rotationUpdate.getProcessor().deltaDotsX;
        rotated = true;
    }

    @Override
    public void onPostFlyingBlockPlace(BlockPlace place) {
        if (rotated && !player.inVehicle()) {
            if (deltaX > 2) {
                float xDiff = Math.abs(deltaX - lastPlacedDeltaX);
                double xDiffDots = Math.abs(deltaDotsX - lastPlacedDeltaDotsX);

                if (xDiff < 0.0001) {
                    flag(V.write(verbose()).f64(xDiff).f64(xDiffDots).f64(deltaY));
                } else {
                    reward();
                }
            } else {
                reward();
            }
            this.lastPlacedDeltaX = deltaX;
            this.lastPlacedDeltaDotsX = deltaDotsX;
            rotated = false;
        }
    }
}
