package ac.limey.limeyac.platform.fabric.mc1171;

import ac.limey.limeyac.platform.fabric.AbstractFabricPlatformServer;
import ac.limey.limeyac.platform.api.manager.cloud.CloudPlatformCommandArguments;
import ac.limey.limeyac.platform.fabric.LimeyFabricIntermediaryLoaderPlugin;
import ac.limey.limeyac.platform.fabric.mc1171.player.Fabric1170PlatformPlayer;
import ac.limey.limeyac.platform.fabric.mc1161.Fabric1140PlatformServer;
import ac.limey.limeyac.platform.fabric.mc1161.player.Fabric1161PlatformInventory;
import ac.limey.limeyac.platform.fabric.mc1171.entity.Fabric1170LimeyEntity;
import ac.limey.limeyac.platform.fabric.mc1161.util.convert.Fabric1140ConversionUtil;
import ac.limey.limeyac.platform.fabric.mc1161.util.convert.Fabric1161MessageUtil;
import ac.limey.limeyac.platform.fabric.player.FabricPlatformPlayerFactory;
import ac.limey.limeyac.platform.fabric.utils.convert.IFabricConversionUtil;
import ac.limey.limeyac.platform.fabric.utils.message.IFabricMessageUtil;
import ac.limey.limeyac.utils.lazy.LazyHolder;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.manager.server.ServerVersion;


public class LimeyFabric1170LoaderPlugin extends LimeyFabricIntermediaryLoaderPlugin {

    public LimeyFabric1170LoaderPlugin() {
        this(LimeyFabricIntermediaryLoaderPlugin::createCommandArguments,
                new FabricPlatformPlayerFactory(
                        Fabric1170PlatformPlayer::new,
                        Fabric1170LimeyEntity::new,
                        Fabric1161PlatformInventory::new
                ),
                PacketEvents.getAPI().getServerManager().getVersion().isNewerThan(ServerVersion.V_1_17)
                        ? new Fabric1171PlatformServer() : new Fabric1140PlatformServer(),
                new Fabric1161MessageUtil(),
                new Fabric1140ConversionUtil()
        );
    }

    protected LimeyFabric1170LoaderPlugin(LazyHolder<CloudPlatformCommandArguments> commandArguments,
                                           FabricPlatformPlayerFactory playerFactory,
                                           AbstractFabricPlatformServer platformServer,
                                           IFabricMessageUtil fabricMessageUtil,
                                           IFabricConversionUtil fabricConversionUtil) {
        super(
                commandArguments,
                playerFactory,
                platformServer,
                fabricMessageUtil,
                fabricConversionUtil
        );
    }

    @Override
    public ServerVersion getNativeVersion() {
        return ServerVersion.V_1_17_1;
    }
}
