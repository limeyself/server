package ac.limey.limeyac.checks.type;

import ac.grim.grimac.api.AbstractCheck;
import ac.limey.limeyac.utils.anticheat.update.BlockBreak;

public interface BlockBreakListener extends AbstractCheck {
    void onBlockBreak(BlockBreak blockBreak);
}
