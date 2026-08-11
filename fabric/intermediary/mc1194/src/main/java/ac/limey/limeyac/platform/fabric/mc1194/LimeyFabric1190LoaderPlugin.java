package ac.limey.limeyac.platform.fabric.mc1194;

import ac.limey.limeyac.platform.fabric.AbstractFabricPlatformServer;
import ac.limey.limeyac.platform.api.manager.cloud.CloudPlatformCommandArguments;
import ac.limey.limeyac.platform.fabric.LimeyFabricIntermediaryLoaderPlugin;
import ac.limey.limeyac.platform.fabric.mc1171.LimeyFabric1170LoaderPlugin;
import ac.limey.limeyac.platform.fabric.mc1171.player.Fabric1170PlatformPlayer;
import ac.limey.limeyac.platform.fabric.mc1194.convert.Fabric1190MessageUtil;
import ac.limey.limeyac.platform.fabric.mc1194.entity.Fabric1194LimeyEntity;
import ac.limey.limeyac.platform.fabric.mc1194.player.Fabric1193PlatformInventory;
import ac.limey.limeyac.platform.fabric.mc1161.player.Fabric1161PlatformInventory;
import ac.limey.limeyac.platform.fabric.mc1161.util.convert.Fabric1140ConversionUtil;
import ac.limey.limeyac.platform.fabric.player.FabricPlatformPlayerFactory;
import ac.limey.limeyac.platform.fabric.utils.convert.IFabricConversionUtil;
import ac.limey.limeyac.platform.fabric.utils.message.IFabricMessageUtil;
import ac.limey.limeyac.utils.lazy.LazyHolder;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.manager.server.ServerVersion;


public class LimeyFabric1190LoaderPlugin extends LimeyFabric1170LoaderPlugin {

    public LimeyFabric1190LoaderPlugin() {
        this(
            LimeyFabricIntermediaryLoaderPlugin::createCommandArguments,
            new FabricPlatformPlayerFactory(
                    Fabric1170PlatformPlayer::new,
                    Fabric1194LimeyEntity::new,
                    PacketEvents.getAPI().getServerManager().getVersion().isNewerThan(ServerVersion.V_1_19_2)
                            ? Fabric1193PlatformInventory::new : Fabric1161PlatformInventory::new
            ),
            new Fabric1190PlatformServer(),
            new Fabric1190MessageUtil(),
            new Fabric1140ConversionUtil()
        );
    }

    protected LimeyFabric1190LoaderPlugin(
            LazyHolder<CloudPlatformCommandArguments> commandArguments,
            FabricPlatformPlayerFactory platformPlayerFactory,
            AbstractFabricPlatformServer platformServer,
            IFabricMessageUtil fabricMessageUtil,
            IFabricConversionUtil fabricConversionUtil) {
        super(commandArguments, platformPlayerFactory, platformServer, fabricMessageUtil, fabricConversionUtil);
    }

    @Override
    public ServerVersion getNativeVersion() {
        return ServerVersion.V_1_19_4;
    }
}
