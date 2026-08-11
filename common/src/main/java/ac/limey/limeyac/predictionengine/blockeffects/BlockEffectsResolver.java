package ac.limey.limeyac.predictionengine.blockeffects;

import ac.limey.limeyac.player.LimeyPlayer;
import ac.limey.limeyac.utils.math.Vector3dm;

import java.util.List;

public interface BlockEffectsResolver {

    void applyEffectsFromBlocks(LimeyPlayer player, Vector3dm clientVelocity, boolean onlyApplyVelocity, List<LimeyPlayer.Movement> movements);

}
