package ac.limey.limeyac.command.commands;

import ac.limey.limeyac.LimeyAPI;
import ac.limey.limeyac.command.BuildableCommand;
import ac.limey.limeyac.platform.api.manager.cloud.CloudPlatformCommandArguments;
import ac.limey.limeyac.platform.api.sender.Sender;
import ac.limey.limeyac.utils.anticheat.MessageUtil;
import net.kyori.adventure.text.Component;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.parser.standard.StringParser;
import org.jetbrains.annotations.NotNull;

public class LimeySendAlert implements BuildableCommand {
    @Override
    public void register(CommandManager<Sender> commandManager, CloudPlatformCommandArguments arguments) {
        commandManager.command(
                commandManager.commandBuilder("limey", "limey")
                        .literal("sendalert")
                        .permission("limey.sendalert")
                        .required("message", StringParser.greedyStringParser())
                        .handler(this::handleSendAlert)
        );
    }

    private void handleSendAlert(@NotNull CommandContext<Sender> context) {
        String string = context.get("message");
        string = MessageUtil.replacePlaceholders((Sender) null, string);
        Component message = MessageUtil.miniMessage(string);
        LimeyAPI.INSTANCE.getAlertManager().sendAlert(message, null);
    }
}
