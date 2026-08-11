package ac.limey.limeyac.platform.fabric.utils.thread;

import ac.limey.limeyac.LimeyAPI;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class FabricFutureUtil {
    public static <U> CompletableFuture<U> supplySync(Supplier<U> entityTeleportSupplier) {
        CompletableFuture<U> ret = new CompletableFuture<>();
        LimeyAPI.INSTANCE.getScheduler().getGlobalRegionScheduler().run(LimeyAPI.INSTANCE.getLimeyPlugin(),
                () -> ret.complete(entityTeleportSupplier.get()));
        return ret;
    }
}
