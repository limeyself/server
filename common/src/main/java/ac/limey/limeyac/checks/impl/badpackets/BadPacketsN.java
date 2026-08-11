package ac.limey.limeyac.checks.impl.badpackets;

import ac.limey.limeyac.checks.Check;
import ac.limey.limeyac.checks.CheckData;
import ac.limey.limeyac.player.LimeyPlayer;

@CheckData(name = "BadPacketsN", stableKey = "limey.badpackets.invalid_teleport", description = "Ignored or failed to accept a required server teleport")
public class BadPacketsN extends Check {
    public BadPacketsN(final LimeyPlayer player) {
        super(player);
    }
}
