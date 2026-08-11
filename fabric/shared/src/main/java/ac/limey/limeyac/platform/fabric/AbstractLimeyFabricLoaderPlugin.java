package ac.limey.limeyac.platform.fabric;

import ac.limey.limeyac.LimeyAPI;
import ac.grim.grimac.api.GrimAPIProvider;
import ac.grim.grimac.api.plugin.GrimPlugin;
import ac.limey.limeyac.command.CloudCommandService;
import ac.grim.grimac.internal.plugin.resolver.GrimExtensionManager;
import ac.limey.limeyac.platform.api.PlatformLoader;
import ac.limey.limeyac.platform.api.PlatformServer;
import ac.limey.limeyac.platform.api.command.CommandService;
import ac.limey.limeyac.platform.api.manager.cloud.CloudPlatformCommandArguments;
import ac.limey.limeyac.platform.api.manager.ItemResetHandler;
import ac.limey.limeyac.platform.api.manager.MessagePlaceHolderManager;
import ac.limey.limeyac.platform.api.manager.PermissionRegistrationManager;
import ac.limey.limeyac.platform.api.manager.PlatformPluginManager;
import ac.limey.limeyac.platform.api.player.PlatformPlayerFactory;
import ac.limey.limeyac.platform.api.scheduler.PlatformScheduler;
import ac.limey.limeyac.platform.api.sender.Sender;
import ac.limey.limeyac.platform.api.sender.SenderFactory;
import ac.limey.limeyac.platform.fabric.manager.FabricMessagePlaceHolderManager;
import ac.limey.limeyac.platform.fabric.manager.FabricPlatformPluginManager;
import ac.limey.limeyac.platform.fabric.resolver.FabricResolverRegistrar;
import ac.limey.limeyac.platform.fabric.utils.convert.IFabricConversionUtil;
import ac.limey.limeyac.platform.fabric.utils.message.IFabricMessageUtil;
import ac.limey.limeyac.utils.anticheat.LogUtil;
import ac.limey.limeyac.utils.lazy.LazyHolder;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.PacketEventsAPI;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import lombok.Getter;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.fabric.FabricServerCommandManager;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractLimeyFabricLoaderPlugin<
        P extends PlatformPlayerFactory,
        S extends PlatformServer,
        H extends PlatformScheduler,
        F extends SenderFactory<?>,
        I extends ItemResetHandler,
        A extends CloudPlatformCommandArguments
        > implements PlatformLoader {

    protected final LazyHolder<H> scheduler;
    protected final PacketEventsAPI<?> packetEvents = PacketEvents.getAPI();
    protected final LazyHolder<F> senderFactory;
    protected final LazyHolder<I> itemResetHandler;
    protected final LazyHolder<A> commandArguments;
    protected final LazyHolder<CommandService> commandService = LazyHolder.simple(this::createCommandService);
    protected final LazyHolder<? extends PermissionRegistrationManager> permissionManager;
    protected final GrimPlugin plugin;
    @Getter
    protected final PlatformPluginManager pluginManager = new FabricPlatformPluginManager();
    @Getter
    protected final MessagePlaceHolderManager messagePlaceHolderManager = new FabricMessagePlaceHolderManager();
    protected final P playerFactory;
    protected final S platformServer;
    protected final IFabricMessageUtil fabricMessageUtil;
    @Getter
    protected final IFabricConversionUtil fabricConversionUtil;

    protected AbstractLimeyFabricLoaderPlugin(
            LazyHolder<H> scheduler,
            LazyHolder<F> senderFactory,
            LazyHolder<I> itemResetHandler,
            LazyHolder<A> commandArguments,
            LazyHolder<? extends PermissionRegistrationManager> permissionManager,
            P playerFactory,
            S platformServer,
            IFabricMessageUtil fabricMessageUtil,
            IFabricConversionUtil fabricConversionUtil
    ) {
        this.scheduler = scheduler;
        this.senderFactory = senderFactory;
        this.itemResetHandler = itemResetHandler;
        this.commandArguments = commandArguments;
        this.permissionManager = permissionManager;
        this.playerFactory = playerFactory;
        this.platformServer = platformServer;
        this.fabricMessageUtil = fabricMessageUtil;
        this.fabricConversionUtil = fabricConversionUtil;

        FabricResolverRegistrar resolverRegistrar = new FabricResolverRegistrar();
        GrimExtensionManager extensionManager = LimeyAPI.INSTANCE.getExtensionManager();
        resolverRegistrar.registerAll(extensionManager);
        plugin = extensionManager.getPlugin("Limey");
    }

    @Override
    public H getScheduler() {
        return scheduler.get();
    }

    @Override
    public PacketEventsAPI<?> getPacketEvents() {
        return packetEvents;
    }

    @Override
    public I getItemResetHandler() {
        return itemResetHandler.get();
    }

    @Override
    public CommandService getCommandService() {
        return commandService.get();
    }

    @Override
    public SenderFactory<?> getSenderFactory() {
        return senderFactory.get();
    }

    @Override
    public GrimPlugin getPlugin() {
        return plugin;
    }

    @Override
    public void registerAPIService() {
        GrimAPIProvider.init(LimeyAPI.INSTANCE.getExternalAPI());
    }

    @Override
    public PermissionRegistrationManager getPermissionManager() {
        return permissionManager.get();
    }

    @Override
    public P getPlatformPlayerFactory() {
        return playerFactory;
    }

    @Override
    public S getPlatformServer() {
        return platformServer;
    }

    public IFabricMessageUtil getFabricMessageUtils() {
        return fabricMessageUtil;
    }

    private CommandService createCommandService() {
        try {
            return createPlatformCommandService();
        } catch (Throwable t) {
            LogUtil.warn("IMPORTANT: Command Framework failed to load (Missing Cloud Library?). \n" +
                    "Limey will run without commands enabled!");
            if (!(t instanceof NoClassDefFoundError)) {
                LogUtil.error(t);
            }
            return () -> {};
        }
    }

    protected CommandService createPlatformCommandService() {
        @SuppressWarnings({"rawtypes", "unchecked"})
        CommandManager<@NotNull Sender> manager = new FabricServerCommandManager(
                ExecutionCoordinator.simpleCoordinator(),
                SenderMapper.identity()
        );
        return new CloudCommandService(() -> manager, commandArguments.get());
    }

    public abstract ServerVersion getNativeVersion();
}
