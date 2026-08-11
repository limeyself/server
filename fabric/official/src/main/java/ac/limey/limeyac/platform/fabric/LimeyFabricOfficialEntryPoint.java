package ac.limey.limeyac.platform.fabric;

import ac.limey.limeyac.platform.fabric.inject.FabricMinecraftServerHandle;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

public class LimeyFabricOfficialEntryPoint extends AbstractLimeyFabricEntryPoint<LimeyFabricOfficialLoaderPlugin> {
    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(FabricServerEvents::fireServerStarting);
        ServerLifecycleEvents.SERVER_STOPPING.register(FabricServerEvents::fireServerStopping);
        ServerTickEvents.END_SERVER_TICK.register(FabricServerEvents::fireEndTick);
        initialize(
                "limey26MainLoad",
                LimeyFabricOfficialLoaderPlugin.class,
                true
        );
    }

    @Override
    protected void setPlatformLoader(LimeyFabricOfficialLoaderPlugin platformLoader) {
        LimeyFabricOfficialLoaderPlugin.LOADER = platformLoader;
    }

    @Override
    protected void setNativeServer(FabricMinecraftServerHandle server) {
        LimeyFabricOfficialLoaderPlugin.FABRIC_SERVER = (MinecraftServer) (Object) server;
    }
}
