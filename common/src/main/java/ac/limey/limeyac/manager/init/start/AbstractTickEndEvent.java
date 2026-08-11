package ac.limey.limeyac.manager.init.start;

import ac.limey.limeyac.LimeyAPI;
import ac.limey.limeyac.player.LimeyPlayer;

// Intended for future events we inject all platforms at the end of a tick
public abstract class AbstractTickEndEvent implements StartableInitable {

    @Override
    public void start() {

    }

    protected void onEndOfTick(LimeyPlayer player, boolean flush) {
        player.packetEntityReplication.onEndOfTickEvent(true, flush);
    }

    protected boolean shouldInjectEndTick() {
        return LimeyAPI.INSTANCE.getConfigManager().getConfig().getBooleanElse("Reach.enable-post-packet", false);
    }
}
