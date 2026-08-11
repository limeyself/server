package ac.limey.limeyac.platform.fabric;

import ac.limey.limeyac.platform.fabric.inject.FabricMinecraftServerHandle;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

public class LimeyFabricIntermediaryEntryPoint extends AbstractLimeyFabricEntryPoint<LimeyFabricIntermediaryLoaderPlugin> {
    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(FabricServerEvents::fireServerStarting);
        ServerLifecycleEvents.SERVER_STOPPING.register(FabricServerEvents::fireServerStopping);
        ServerTickEvents.END_SERVER_TICK.register(FabricServerEvents::fireEndTick);
        initialize(
                "limeyMainLoad",
                LimeyFabricIntermediaryLoaderPlugin.class,
                false
        );
    }

    @Override
    protected void setPlatformLoader(LimeyFabricIntermediaryLoaderPlugin platformLoader) {
        LimeyFabricIntermediaryLoaderPlugin.LOADER = platformLoader;
    }

    @Override
    protected void setNativeServer(FabricMinecraftServerHandle server) {
        LimeyFabricIntermediaryLoaderPlugin.FABRIC_SERVER = (MinecraftServer) server;
    }
}
