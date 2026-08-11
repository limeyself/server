package ac.limey.limeyac.checks.debug;

import ac.limey.limeyac.checks.Check;
import ac.limey.limeyac.player.LimeyPlayer;

public abstract class AbstractDebugHandler extends Check {
    public AbstractDebugHandler(LimeyPlayer player) {
        super(player);
    }

    public abstract void toggleListener(LimeyPlayer player);

    public abstract boolean toggleConsoleOutput();
}
