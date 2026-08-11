package ac.limey.limeyac.manager.tick.impl;

import ac.limey.limeyac.LimeyAPI;
import ac.limey.limeyac.manager.tick.Tickable;
import ac.limey.limeyac.player.LimeyPlayer;

public class ClearRecentlyUpdatedBlocks implements Tickable {

    private static final int maxTickAge = 2;

    @Override
    public void tick() {
        for (LimeyPlayer player : LimeyAPI.INSTANCE.getPlayerDataManager().getEntries()) {
            player.blockHistory.cleanup(LimeyAPI.INSTANCE.getTickManager().currentTick - maxTickAge);
        }
    }
}
