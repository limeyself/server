package ac.limey.limeyac.command.requirements;

import ac.limey.limeyac.command.SenderRequirement;
import ac.limey.limeyac.platform.api.sender.Sender;
import ac.limey.limeyac.utils.anticheat.MessageUtil;
import net.kyori.adventure.text.Component;
import org.incendo.cloud.context.CommandContext;
import org.jetbrains.annotations.NotNull;

public enum PlayerSenderRequirement implements SenderRequirement {
    INSTANCE;

    @Override
    public @NotNull Component errorMessage(Sender sender) {
        return MessageUtil.getParsedComponent(sender, "run-as-player", "%prefix% &cThis command can only be used by players!");
    }

    @Override
    public boolean evaluateRequirement(@NotNull CommandContext<Sender> commandContext) {
        return commandContext.sender().isPlayer();
    }
}
