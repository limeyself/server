package ac.limey.limeyac.utils.blockplace;

import ac.limey.limeyac.player.LimeyPlayer;
import ac.limey.limeyac.utils.anticheat.update.BlockPlace;

public interface BlockPlaceFactory {
    void applyBlockPlaceToWorld(LimeyPlayer player, BlockPlace place);
}
