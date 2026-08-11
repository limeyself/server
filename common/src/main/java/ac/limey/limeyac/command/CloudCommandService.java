package ac.limey.limeyac.command;

import ac.limey.limeyac.command.commands.*;
import ac.limey.limeyac.command.handler.LimeyCommandFailureHandler;
import ac.limey.limeyac.platform.api.command.CommandService;
import ac.limey.limeyac.platform.api.manager.cloud.CloudPlatformCommandArguments;
import ac.limey.limeyac.platform.api.sender.Sender;
import io.leangen.geantyref.TypeToken;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.format.NamedTextColor;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.exception.InvalidSyntaxException;
import org.incendo.cloud.key.CloudKey;
import org.incendo.cloud.processors.requirements.RequirementApplicable;
import org.incendo.cloud.processors.requirements.RequirementApplicable.RequirementApplicableFactory;
import org.incendo.cloud.processors.requirements.RequirementPostprocessor;
import org.incendo.cloud.processors.requirements.Requirements;

import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;

public class CloudCommandService implements CommandService {

    public static final CloudKey<Requirements<Sender, SenderRequirement>> REQUIREMENT_KEY
            = CloudKey.of("requirements", new TypeToken<>() {});

    public static final RequirementApplicableFactory<Sender, SenderRequirement> REQUIREMENT_FACTORY
            = RequirementApplicable.factory(REQUIREMENT_KEY);

    private boolean commandsRegistered = false;

    private final Supplier<CommandManager<Sender>> commandManagerSupplier;
    private final CloudPlatformCommandArguments commandArguments;

    public CloudCommandService(Supplier<CommandManager<Sender>> commandManagerSupplier, CloudPlatformCommandArguments commandArguments) {
        this.commandManagerSupplier = commandManagerSupplier;
        this.commandArguments = commandArguments;
    }

    public void registerCommands() {
        if (commandsRegistered) return;
        CommandManager<Sender> commandManager = commandManagerSupplier.get();
        new LimeyPerf().register(commandManager, commandArguments);
        new LimeyDebug().register(commandManager, commandArguments);
        new LimeyAlerts().register(commandManager, commandArguments);
        new LimeyProfile().register(commandManager, commandArguments);
        new LimeySendAlert().register(commandManager, commandArguments);
        new LimeyHelp().register(commandManager, commandArguments);
        new LimeyHistory().register(commandManager, commandArguments);
        new LimeyHistoryMigrate().register(commandManager, commandArguments);
        new LimeyHistoryCopy().register(commandManager, commandArguments);
        new LimeyReload().register(commandManager, commandArguments);
        new LimeySpectate().register(commandManager, commandArguments);
        new LimeyStopSpectating().register(commandManager, commandArguments);
        new LimeyLog().register(commandManager, commandArguments);
        new LimeyVerbose().register(commandManager, commandArguments);
        new LimeyVersion().register(commandManager, commandArguments);
        new LimeyDump().register(commandManager, commandArguments);
        new LimeyBrands().register(commandManager, commandArguments);
        new LimeyList().register(commandManager, commandArguments);
        new LimeyTestWebhook().register(commandManager, commandArguments);

        final RequirementPostprocessor<Sender, SenderRequirement>
                senderRequirementPostprocessor = RequirementPostprocessor.of(
                REQUIREMENT_KEY,
                new LimeyCommandFailureHandler()
        );
        commandManager.registerCommandPostProcessor(senderRequirementPostprocessor);
        registerInvalidSyntaxHandler(commandManager);
        commandsRegistered = true;
    }

    private void registerInvalidSyntaxHandler(CommandManager<Sender> commandManager) {
        commandManager.exceptionController().registerHandler(InvalidSyntaxException.class, context -> {
            Sender sender = context.context().sender();
            if (isHistoryInput(context.context().rawInput().input())) {
                sender.sendMessage(Component.text("Invalid history syntax.", NamedTextColor.RED));
                sender.sendMessage(Component.text("Use: /limey history <player> [page <N>]", NamedTextColor.GRAY));
                sender.sendMessage(Component.text("Use: /limey history <player> session <N|latest> [page <N>] [-d] [-v]", NamedTextColor.GRAY));
                sender.sendMessage(Component.text("Tip: /limey history <player> session shows filter and detail options.", NamedTextColor.GRAY));
                sender.sendMessage(Component.text("Use /limey history player <player> ... for names that collide with history subcommands.", NamedTextColor.GRAY));
                return;
            }
            sender.sendMessage(Component.text(context.exception().correctSyntax(), NamedTextColor.RED));
        });
    }

    private static boolean isHistoryInput(String rawInput) {
        String input = rawInput.strip();
        if (input.startsWith("/")) input = input.substring(1).strip();
        String[] tokens = input.toLowerCase(Locale.ROOT).split("\\s+");
        return tokens.length >= 2
                && (tokens[0].equals("limey") || tokens[0].equals("limey"))
                && (tokens[1].equals("history") || tokens[1].equals("hist"));
    }

    protected <E extends Exception> void registerExceptionHandler(CommandManager<Sender> commandManager, Class<E> ex, Function<E, ComponentLike> toComponent) {
        commandManager.exceptionController().registerHandler(ex,
                (c) -> c.context().sender().sendMessage(toComponent.apply(c.exception()).asComponent().colorIfAbsent(NamedTextColor.RED))
        );
    }
}
