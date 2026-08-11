package ac.limey.limeyac.command.commands;

import ac.limey.limeyac.LimeyAPI;
import ac.limey.limeyac.command.BuildableCommand;
import ac.limey.limeyac.platform.api.manager.cloud.CloudPlatformCommandArguments;
import ac.limey.limeyac.platform.api.sender.Sender;
import ac.limey.limeyac.utils.anticheat.LogUtil;
import ac.limey.limeyac.utils.anticheat.MessageUtil;
import ac.limey.limeyac.utils.data.webhook.discord.WebhookMessage;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.context.CommandContext;
import org.jetbrains.annotations.NotNull;

public class LimeyTestWebhook implements BuildableCommand {
    @Override
    public void register(CommandManager<Sender> commandManager, CloudPlatformCommandArguments arguments) {
        commandManager.command(
                commandManager.commandBuilder("limey", "limey")
                        .literal("testwebhook")
                        .permission("limey.testwebhook")
                        .handler(this::handleTestWebhook)
        );
    }

    private void handleTestWebhook(@NotNull CommandContext<Sender> context) {
        if (LimeyAPI.INSTANCE.getDiscordManager().isDisabled()) {
            context.sender().sendMessage(MessageUtil.miniMessage(LimeyAPI.INSTANCE.getConfigManager().getWebhookNotEnabled()));
            return;
        }

        WebhookMessage webhookMessage = new WebhookMessage().content(LimeyAPI.INSTANCE.getConfigManager().getWebhookTestMessage());
        LimeyAPI.INSTANCE.getDiscordManager().sendWebhookMessage(webhookMessage).whenCompleteAsync(((successful, throwable) -> {
            if (successful == true) {
                context.sender().sendMessage(MessageUtil.miniMessage(LimeyAPI.INSTANCE.getConfigManager().getWebhookTestSucceeded()));
                return;
            }

            context.sender().sendMessage(MessageUtil.miniMessage(LimeyAPI.INSTANCE.getConfigManager().getWebhookTestFailed()));

            if (throwable != null) {
                LogUtil.error("Exception caught while sending a Discord webhook test alert", throwable);
            }
        }));
    }
}
