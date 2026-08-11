package ac.limey.limeyac.manager.tick.impl;

import ac.limey.limeyac.LimeyAPI;
import ac.limey.limeyac.manager.tick.Tickable;
import ac.limey.limeyac.player.LimeyPlayer;

public class ResetTick implements Tickable {
    @Override
    public void tick() {
        for (LimeyPlayer player : LimeyAPI.INSTANCE.getPlayerDataManager().getEntries()) {
            player.packetEntityReplication.tickStartTick();
        }
    }
}
