package ac.limey.limeyac.platform.bukkit.initables;

import ac.limey.limeyac.manager.init.start.StartableInitable;
import ac.limey.limeyac.platform.bukkit.LimeyBukkitLoaderPlugin;
import ac.limey.limeyac.platform.bukkit.events.PistonEvent;
import ac.limey.limeyac.utils.anticheat.LogUtil;
import org.bukkit.Bukkit;

public class BukkitEventManager implements StartableInitable {
    public void start() {
        LogUtil.info("Registering singular bukkit event... (PistonEvent)");

        Bukkit.getPluginManager().registerEvents(new PistonEvent(), LimeyBukkitLoaderPlugin.LOADER);
    }
}
