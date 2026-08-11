package ac.limey.limeyac.platform.bukkit.initables;

import ac.limey.limeyac.manager.init.start.StartableInitable;
import ac.limey.limeyac.platform.bukkit.LimeyBukkitLoaderPlugin;
import ac.limey.limeyac.utils.anticheat.Constants;
import io.github.retrooper.packetevents.bstats.bukkit.Metrics;

public class BukkitBStats implements StartableInitable {
    @Override
    public void start() {
        try {
            new Metrics(LimeyBukkitLoaderPlugin.LOADER, Constants.BSTATS_PLUGIN_ID);
        } catch (Exception ignored) {}
    }
}
