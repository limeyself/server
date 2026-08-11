package ac.limey.limeyac.platform.fabric;

import ac.limey.limeyac.platform.fabric.manager.FabricItemResetHandler;
import ac.limey.limeyac.platform.fabric.manager.FabricCloudPlatformCommandArguments;
import ac.limey.limeyac.platform.fabric.manager.FabricPermissionRegistrationManager;
import ac.limey.limeyac.platform.fabric.command.FabricPlayerSelectorParser;
import ac.limey.limeyac.platform.fabric.player.FabricPlatformPlayerFactory;
import ac.limey.limeyac.platform.fabric.scheduler.FabricPlatformScheduler;
import ac.limey.limeyac.platform.fabric.sender.AbstractFabricSenderFactory;
import ac.limey.limeyac.platform.fabric.sender.FabricOfficialSenderFactory;
import me.lucko.fabric.api.permissions.v0.Permissions;
import ac.limey.limeyac.platform.fabric.utils.FabricOfficialPolymerHook;
import ac.limey.limeyac.platform.fabric.utils.convert.IFabricConversionUtil;
import ac.limey.limeyac.platform.fabric.utils.message.IFabricMessageUtil;
import ac.limey.limeyac.utils.lazy.LazyHolder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public abstract class LimeyFabricOfficialLoaderPlugin extends AbstractLimeyFabricLoaderPlugin<
        FabricPlatformPlayerFactory,
        AbstractFabricPlatformServer,
        FabricPlatformScheduler,
        FabricOfficialSenderFactory,
        FabricItemResetHandler,
        FabricCloudPlatformCommandArguments
        > {
    public static MinecraftServer FABRIC_SERVER;
    public static LimeyFabricOfficialLoaderPlugin LOADER;

    public LimeyFabricOfficialLoaderPlugin(
            FabricPlatformPlayerFactory playerFactory,
            AbstractFabricPlatformServer platformServer,
            IFabricMessageUtil fabricMessageUtil,
            IFabricConversionUtil fabricConversionUtil
    ) {
        super(
                LazyHolder.simple(FabricPlatformScheduler::new),
                LazyHolder.simple(FabricOfficialSenderFactory::new),
                LazyHolder.simple(() -> new FabricItemResetHandler(fabricConversionUtil)),
                LazyHolder.simple(LimeyFabricOfficialLoaderPlugin::createCommandArguments),
                LazyHolder.simple(() -> new FabricPermissionRegistrationManager(
                        LOADER.getFabricSenderFactory(),
                        name -> {
                            if (AbstractFabricSenderFactory.HAS_PERMISSIONS_API) {
                                Permissions.check(FABRIC_SERVER.createCommandSourceStack(), name);
                            }
                        })),
                playerFactory,
                platformServer,
                fabricMessageUtil,
                fabricConversionUtil
        );
        FabricPlatformServices.configure(
                playerFactory::getPlatformInventory,
                playerFactory::getPlatformEntity,
                player -> FabricOfficialPolymerHook.createTranslator((ServerPlayer) player),
                fabricMessageUtil::textLiteral,
                platformServer::getProfileByName,
                fabricConversionUtil
        );
    }

    public FabricOfficialSenderFactory getFabricSenderFactory() {
        return senderFactory.get();
    }

    public static FabricCloudPlatformCommandArguments createCommandArguments() {
        return new FabricCloudPlatformCommandArguments(new FabricPlayerSelectorParser<>(
                selector -> LOADER.getFabricSenderFactory().wrap(selector.single().createCommandSourceStack()),
                selector -> selector.inputString()
        ));
    }

}
