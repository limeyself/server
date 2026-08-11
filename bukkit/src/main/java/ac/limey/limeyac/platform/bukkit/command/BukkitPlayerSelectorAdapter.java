package ac.limey.limeyac.platform.bukkit.command;

import ac.limey.limeyac.LimeyAPI;
import ac.limey.limeyac.platform.api.command.PlayerSelector;
import ac.limey.limeyac.platform.api.sender.Sender;
import ac.limey.limeyac.platform.bukkit.sender.BukkitSenderFactory;
import lombok.RequiredArgsConstructor;
import org.incendo.cloud.bukkit.data.SinglePlayerSelector;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class BukkitPlayerSelectorAdapter implements PlayerSelector {
    private final SinglePlayerSelector bukkitSelector;

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Sender getSinglePlayer() {
        return ((BukkitSenderFactory) LimeyAPI.INSTANCE.getSenderFactory()).map(bukkitSelector.single());
    }

    @Override
    public Collection<Sender> getPlayers() {
        return Collections.singletonList(((BukkitSenderFactory) LimeyAPI.INSTANCE.getSenderFactory()).map(bukkitSelector.single()));
    }

    @Override
    public String inputString() {
        return bukkitSelector.inputString();
    }
}
