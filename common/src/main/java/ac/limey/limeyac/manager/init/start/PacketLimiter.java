package ac.limey.limeyac.manager.init.start;

import ac.limey.limeyac.LimeyAPI;
import ac.limey.limeyac.player.LimeyPlayer;

public class PacketLimiter implements StartableInitable {
    @Override
    public void start() {
        LimeyAPI.INSTANCE.getScheduler().getAsyncScheduler().runAtFixedRate(LimeyAPI.INSTANCE.getLimeyPlugin(), () -> {
            for (LimeyPlayer player : LimeyAPI.INSTANCE.getPlayerDataManager().getEntries()) {
                // Avoid concurrent reading on an integer as it's results are unknown
                player.cancelledPackets.set(0);
            }
        }, 1, 20);
    }
}
