package ac.limey.limeyac.checks.type;

import ac.grim.grimac.api.AbstractCheck;
import ac.limey.limeyac.utils.anticheat.update.BlockPlace;

public interface PostFlyingBlockPlaceListener extends AbstractCheck {
    void onPostFlyingBlockPlace(BlockPlace place);
}
