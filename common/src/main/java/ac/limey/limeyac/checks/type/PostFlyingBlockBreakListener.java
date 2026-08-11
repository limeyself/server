package ac.limey.limeyac.checks.type;

import ac.grim.grimac.api.AbstractCheck;
import ac.limey.limeyac.utils.anticheat.update.BlockBreak;

public interface PostFlyingBlockBreakListener extends AbstractCheck {
    void onPostFlyingBlockBreak(BlockBreak blockBreak);
}
