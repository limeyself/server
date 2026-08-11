package ac.limey.limeyac.checks.impl.badpackets;

import ac.limey.limeyac.checks.Check;
import ac.limey.limeyac.checks.CheckData;
import ac.limey.limeyac.player.LimeyPlayer;

@CheckData(name = "BadPacketsB", stableKey = "limey.badpackets.ignored_rotation", description = "Ignored set rotation packet")
public class BadPacketsB extends Check {
    public BadPacketsB(final LimeyPlayer player) {
        super(player);
    }
}
