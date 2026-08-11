package ac.limey.limeyac.checks.type;

import ac.grim.grimac.api.AbstractCheck;
import ac.limey.limeyac.utils.anticheat.update.RotationUpdate;

public interface RotationListener extends AbstractCheck {
    void process(RotationUpdate rotationUpdate);
}
