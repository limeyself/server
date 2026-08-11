package ac.limey.limeyac.checks.type;

import ac.grim.grimac.api.AbstractCheck;
import ac.limey.limeyac.utils.anticheat.update.PositionUpdate;

public interface PositionListener extends AbstractCheck {
    void onPositionUpdate(PositionUpdate positionUpdate);
}
