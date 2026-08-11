package ac.limey.limeyac.checks.impl.combat;

import ac.limey.limeyac.checks.Check;
import ac.limey.limeyac.checks.CheckData;
import ac.limey.limeyac.player.LimeyPlayer;

@CheckData(name = "Hitboxes", stableKey = "limey.combat.hitboxes", description = "Tried to hit an entity outside its valid hitbox")
public class Hitboxes extends Check {
    public Hitboxes(LimeyPlayer player) {
        super(player);
    }
}
