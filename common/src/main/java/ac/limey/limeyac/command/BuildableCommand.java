package ac.limey.limeyac.command;

import ac.limey.limeyac.platform.api.manager.cloud.CloudPlatformCommandArguments;
import ac.limey.limeyac.platform.api.sender.Sender;
import org.incendo.cloud.CommandManager;

public interface BuildableCommand {
    void register(CommandManager<Sender> manager, CloudPlatformCommandArguments arguments);
}
