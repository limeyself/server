package ac.limey.limeyac.command.commands;

import ac.limey.limeyac.LimeyAPI;
import ac.limey.limeyac.command.BuildableCommand;
import ac.limey.limeyac.manager.AlertManagerImpl;
import ac.limey.limeyac.manager.datastore.PlayerToggleStore;
import ac.limey.limeyac.platform.api.manager.cloud.CloudPlatformCommandArguments;
import ac.limey.limeyac.platform.api.player.PlatformPlayer;
import ac.limey.limeyac.platform.api.sender.Sender;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.description.Description;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class LimeyAlerts implements BuildableCommand {
    @Override
    public void register(CommandManager<Sender> commandManager, CloudPlatformCommandArguments arguments) {
        commandManager.command(
                commandManager.commandBuilder("limey", "limey")
                        .literal("alerts", Description.of("Toggle alerts for the sender"))
                        .permission("limey.alerts")
                        .handler(this::handleAlerts)
        );
    }

    private void handleAlerts(@NotNull CommandContext<Sender> context) {
        Sender sender = context.sender();
        if (sender.isPlayer()) {
            PlatformPlayer player = Objects.requireNonNull(context.sender().getPlatformPlayer(), "player");
            AlertManagerImpl am = LimeyAPI.INSTANCE.getAlertManager();
            boolean newState = !am.hasAlertsEnabled(player);
            am.setAlertsEnabled(player, newState, false);
            LimeyAPI.INSTANCE.getDataStoreLifecycle().playerToggleStore()
                    .applyUserToggle(player.getUniqueId(), PlayerToggleStore.KEY_ALERTS, newState);
        } else if (sender.isConsole()) {
            LimeyAPI.INSTANCE.getAlertManager().toggleConsoleAlerts();
        }
    }
}
