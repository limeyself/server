package ac.limey.limeyac.manager.init.start;

import ac.limey.limeyac.LimeyAPI;
import ac.limey.limeyac.platform.api.Platform;
import ac.limey.limeyac.utils.anticheat.LogUtil;

public class TickRunner implements StartableInitable {
    @Override
    public void start() {
        LogUtil.info("Registering tick schedulers...");

        if (LimeyAPI.INSTANCE.getPlatform() == Platform.FOLIA) {
            LimeyAPI.INSTANCE.getScheduler().getAsyncScheduler().runAtFixedRate(LimeyAPI.INSTANCE.getLimeyPlugin(), () -> {
                LimeyAPI.INSTANCE.getTickManager().tickSync();
                LimeyAPI.INSTANCE.getTickManager().tickAsync();
            }, 1, 1);
        } else {
            LimeyAPI.INSTANCE.getScheduler().getGlobalRegionScheduler().runAtFixedRate(LimeyAPI.INSTANCE.getLimeyPlugin(), () -> LimeyAPI.INSTANCE.getTickManager().tickSync(), 0, 1);
            LimeyAPI.INSTANCE.getScheduler().getAsyncScheduler().runAtFixedRate(LimeyAPI.INSTANCE.getLimeyPlugin(), () -> LimeyAPI.INSTANCE.getTickManager().tickAsync(), 0, 1);
        }
    }
}
