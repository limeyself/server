package ac.limey.limeyac.platform.fabric.initables;

import ac.limey.limeyac.LimeyAPI;
import ac.limey.limeyac.manager.init.start.AbstractTickEndEvent;
import ac.limey.limeyac.platform.fabric.FabricServerEvents;
import ac.limey.limeyac.player.LimeyPlayer;

public class FabricTickEndEvent extends AbstractTickEndEvent {

    @Override
    public void start() {
        if (!super.shouldInjectEndTick()) {
            return;
        }

        FabricServerEvents.onEndTick(server -> tickAllPlayers());
    }

    private void tickAllPlayers() {
        for (LimeyPlayer player : LimeyAPI.INSTANCE.getPlayerDataManager().getEntries()) {
            if (player.disableLimey) continue;
            super.onEndOfTick(player, true);
        }
    }
}
