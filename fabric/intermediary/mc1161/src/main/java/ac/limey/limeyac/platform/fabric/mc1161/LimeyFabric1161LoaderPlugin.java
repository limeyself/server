package ac.limey.limeyac.platform.fabric.mc1161;

import ac.limey.limeyac.platform.fabric.AbstractFabricPlatformServer;
import ac.limey.limeyac.platform.fabric.LimeyFabricIntermediaryLoaderPlugin;
import ac.limey.limeyac.platform.fabric.mc1161.entity.Fabric1161LimeyEntity;
import ac.limey.limeyac.platform.fabric.mc1161.player.Fabric1161PlatformInventory;
import ac.limey.limeyac.platform.fabric.mc1161.player.Fabric1161PlatformPlayer;
import ac.limey.limeyac.platform.fabric.mc1161.util.convert.Fabric1140ConversionUtil;
import ac.limey.limeyac.platform.fabric.mc1161.util.convert.Fabric1161MessageUtil;
import ac.limey.limeyac.platform.fabric.player.FabricPlatformPlayerFactory;
import ac.limey.limeyac.platform.fabric.utils.convert.IFabricConversionUtil;
import ac.limey.limeyac.platform.fabric.utils.message.IFabricMessageUtil;
import com.github.retrooper.packetevents.manager.server.ServerVersion;

public class LimeyFabric1161LoaderPlugin extends LimeyFabricIntermediaryLoaderPlugin {

    public LimeyFabric1161LoaderPlugin() {
        this(
            new FabricPlatformPlayerFactory(
                Fabric1161PlatformPlayer::new,
                Fabric1161LimeyEntity::new,
                Fabric1161PlatformInventory::new
            ),
            new Fabric1140PlatformServer(),
            new Fabric1161MessageUtil(),
            new Fabric1140ConversionUtil()
        );
    }

    protected LimeyFabric1161LoaderPlugin(
            FabricPlatformPlayerFactory playerFactory,
            AbstractFabricPlatformServer platformServer,
            IFabricMessageUtil fabricMessageUtil,
            IFabricConversionUtil fabricConversionUtil
    ) {
        super(LimeyFabricIntermediaryLoaderPlugin::createCommandArguments,
            playerFactory,
            platformServer,
            fabricMessageUtil,
            fabricConversionUtil
        );
    }

    @Override
    public ServerVersion getNativeVersion() {
        return ServerVersion.V_1_16_1;
    }
}
