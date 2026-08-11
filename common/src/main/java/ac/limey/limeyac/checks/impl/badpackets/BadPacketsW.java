package ac.limey.limeyac.checks.impl.badpackets;

import ac.limey.limeyac.checks.Check;
import ac.limey.limeyac.checks.CheckData;
import ac.limey.limeyac.player.LimeyPlayer;

@CheckData(name = "BadPacketsW", stableKey = "limey.badpackets.invalid_entity_target", description = "Interacted with non-existent entity", experimental = true)
public class BadPacketsW extends Check {
    public BadPacketsW(LimeyPlayer player) {
        super(player);
    }
}
