package ac.limey.limeyac.platform.api.manager.cloud;

import ac.limey.limeyac.platform.api.command.PlayerSelector;
import ac.limey.limeyac.platform.api.sender.Sender;
import org.incendo.cloud.parser.ParserDescriptor;
import org.incendo.cloud.suggestion.SuggestionProvider;

public interface CloudPlatformCommandArguments {
    ParserDescriptor<Sender, PlayerSelector> singlePlayerSelectorParser();

    SuggestionProvider<Sender> onlinePlayerSuggestions();
}
