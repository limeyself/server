package ac.limey.limeyac;

import ac.grim.grimac.api.event.EventBus;
import ac.grim.grimac.api.plugin.GrimPlugin;
import ac.grim.grimac.api.storage.backend.BackendRegistry;
import ac.grim.grimac.internal.plugin.resolver.GrimExtensionManager;
import ac.grim.grimac.internal.event.OptimizedEventBus;
import ac.grim.grimac.internal.storage.backend.BackendRegistryImpl;
import ac.grim.grimac.internal.storage.backend.memory.InMemoryBackendProvider;
import ac.grim.grimac.internal.storage.backend.mongo.MongoBackendProvider;
import ac.grim.grimac.internal.storage.backend.mysql.MysqlBackendProvider;
import ac.grim.grimac.internal.storage.backend.postgres.PostgresBackendProvider;
import ac.grim.grimac.internal.storage.backend.redis.RedisBackendProvider;
import ac.grim.grimac.internal.storage.backend.sqlite.SqliteBackendProvider;
import ac.limey.limeyac.manager.AlertManagerImpl;
import ac.limey.limeyac.manager.DiscordManager;
import ac.limey.limeyac.manager.InitManager;
import ac.limey.limeyac.manager.SpectateManager;
import ac.limey.limeyac.manager.TickManager;
import ac.limey.limeyac.manager.config.BaseConfigManager;
import ac.limey.limeyac.manager.datastore.DataStoreLifecycle;
import ac.limey.limeyac.manager.init.Initable;
import ac.limey.limeyac.platform.api.Platform;
import ac.limey.limeyac.platform.api.PlatformLoader;
import ac.limey.limeyac.platform.api.PlatformServer;
import ac.limey.limeyac.platform.api.command.CommandService;
import ac.limey.limeyac.platform.api.manager.ItemResetHandler;
import ac.limey.limeyac.platform.api.manager.MessagePlaceHolderManager;
import ac.limey.limeyac.platform.api.manager.PermissionRegistrationManager;
import ac.limey.limeyac.platform.api.manager.PlatformPluginManager;
import ac.limey.limeyac.platform.api.player.PlatformPlayerFactory;
import ac.limey.limeyac.platform.api.scheduler.PlatformScheduler;
import ac.limey.limeyac.platform.api.sender.SenderFactory;
import ac.limey.limeyac.utils.anticheat.PlayerDataManager;
import ac.limey.limeyac.utils.common.arguments.CommonLimeyArguments;
import ac.limey.limeyac.utils.reflection.ReflectionUtils;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public final class LimeyAPI {
    public static final LimeyAPI INSTANCE = new LimeyAPI();

    private final Platform platform = detectPlatform();
    private final BaseConfigManager configManager;
    private final AlertManagerImpl alertManager;
    private final SpectateManager spectateManager;
    private final DiscordManager discordManager;
    private final PlayerDataManager playerDataManager;
    private final TickManager tickManager;
    private final GrimExtensionManager extensionManager;
    private final EventBus eventBus;
    private final LimeyExternalAPI externalAPI;
    private DataStoreLifecycle dataStoreLifecycle;
    private final BackendRegistry backendRegistry = buildBackendRegistry();
    private PlatformLoader loader;
    private InitManager initManager;
    private boolean initialized = false;

    private LimeyAPI() {
        this.configManager = new BaseConfigManager();
        this.alertManager = new AlertManagerImpl();
        this.spectateManager = new SpectateManager();
        this.discordManager = new DiscordManager();
        this.playerDataManager = new PlayerDataManager();
        this.tickManager = new TickManager();
        this.extensionManager = new GrimExtensionManager();
        this.eventBus = new OptimizedEventBus(extensionManager);
        this.externalAPI = new LimeyExternalAPI(this);
    }

    // the order matters
    private static Platform detectPlatform() {
        Platform override = CommonLimeyArguments.PLATFORM_OVERRIDE.value();
        if (override != null) return override;
        if (ReflectionUtils.hasClass("io.papermc.paper.threadedregions.RegionizedServer")) return Platform.FOLIA;
        if (ReflectionUtils.hasClass("org.bukkit.Bukkit")) return Platform.BUKKIT;
        if (ReflectionUtils.hasClass("net.fabricmc.loader.api.FabricLoader")) return Platform.FABRIC;
        throw new IllegalStateException("Unknown platform!");
    }

    public void load(PlatformLoader platformLoader, Initable... platformSpecificInitables) {
        this.loader = platformLoader;
        this.dataStoreLifecycle = new DataStoreLifecycle(getLimeyPlugin(), backendRegistry);
        this.initManager = new InitManager(loader.getPacketEvents(), platformSpecificInitables);
        this.initManager.load();
        this.initialized = true;
    }

    private static BackendRegistry buildBackendRegistry() {
        BackendRegistryImpl registry = new BackendRegistryImpl();
        registry.register(new SqliteBackendProvider());
        registry.register(new InMemoryBackendProvider());
        registry.register(new MysqlBackendProvider());
        registry.register(new PostgresBackendProvider());
        registry.register(new MongoBackendProvider());
        registry.register(new RedisBackendProvider());
        return registry;
    }

    public void start() {
        checkInitialized();
        initManager.start();
    }

    public void stop() {
        checkInitialized();
        initManager.stop();
    }

    public PlatformScheduler getScheduler() {
        return loader.getScheduler();
    }

    public PlatformPlayerFactory getPlatformPlayerFactory() {
        return loader.getPlatformPlayerFactory();
    }

    // NOTE: returns the external published GrimPlugin type (ac.grim.grimac:GrimAPI).
    // The type is kept Grim-prefixed because it comes from the upstream API artifact.
    public GrimPlugin getLimeyPlugin() {
        return loader.getPlugin();
    }

    public SenderFactory<?> getSenderFactory() {
        return loader.getSenderFactory();
    }

    public ItemResetHandler getItemResetHandler() {
        return loader.getItemResetHandler();
    }

    public PlatformPluginManager getPluginManager() {
        return loader.getPluginManager();
    }

    public PlatformServer getPlatformServer() {
        return loader.getPlatformServer();
    }

    public @NotNull MessagePlaceHolderManager getMessagePlaceHolderManager() {
        return loader.getMessagePlaceHolderManager();
    }

    public CommandService getCommandService() {
        return loader.getCommandService();
    }

    private void checkInitialized() {
        if (!initialized) {
            throw new IllegalStateException("LimeyAPI has not been initialized!");
        }
    }

    public PermissionRegistrationManager getPermissionManager() {
        return loader.getPermissionManager();
    }
}
