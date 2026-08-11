package ac.limey.limeyac.manager.tick.impl;

import ac.limey.limeyac.LimeyAPI;
import ac.limey.limeyac.manager.config.BaseConfigManager;
import ac.limey.limeyac.manager.tick.Tickable;
import ac.limey.limeyac.player.LimeyPlayer;

public class TickPermissions implements Tickable {

    @Override
    public void tick() {
        BaseConfigManager config = LimeyAPI.INSTANCE.getConfigManager();
        int interval = config.getUpdatePermissionTicks();
        if (interval <= 0 || LimeyAPI.INSTANCE.getTickManager().currentTick % interval != 0) return;

        for (LimeyPlayer player : LimeyAPI.INSTANCE.getPlayerDataManager().getEntries()) {
            player.updatePermissions();
        }
    }
}
