package ac.limey.limeyac.platform.bukkit.scheduler.bukkit;

import ac.grim.grimac.api.plugin.GrimPlugin;
import ac.limey.limeyac.platform.api.scheduler.AsyncScheduler;
import ac.limey.limeyac.platform.api.scheduler.PlatformScheduler;
import ac.limey.limeyac.platform.api.scheduler.TaskHandle;
import ac.limey.limeyac.platform.bukkit.LimeyBukkitLoaderPlugin;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class BukkitAsyncScheduler implements AsyncScheduler {

    private final BukkitScheduler bukkitScheduler = Bukkit.getScheduler();

    @Override
    public TaskHandle runNow(@NotNull GrimPlugin plugin, @NotNull Runnable task) {
        return new BukkitTaskHandle(bukkitScheduler.runTaskAsynchronously(LimeyBukkitLoaderPlugin.LOADER, task));
    }

    @Override
    public TaskHandle runDelayed(@NotNull GrimPlugin plugin, @NotNull Runnable task, long delay, @NotNull TimeUnit timeUnit) {
        return new BukkitTaskHandle(bukkitScheduler.runTaskLaterAsynchronously(
                LimeyBukkitLoaderPlugin.LOADER,
                task,
                PlatformScheduler.convertTimeToTicks(delay, timeUnit)
        ));
    }

    @Override
    public TaskHandle runAtFixedRate(@NotNull GrimPlugin plugin, @NotNull Runnable task, long delay, long period, @NotNull TimeUnit timeUnit) {
        return new BukkitTaskHandle(bukkitScheduler.runTaskTimerAsynchronously(
                LimeyBukkitLoaderPlugin.LOADER,
                task,
                PlatformScheduler.convertTimeToTicks(delay, timeUnit),
                PlatformScheduler.convertTimeToTicks(period, timeUnit)
        ));
    }

    @Override
    public TaskHandle runAtFixedRate(@NotNull GrimPlugin plugin, @NotNull Runnable task, long initialDelayTicks, long periodTicks) {
        return new BukkitTaskHandle(bukkitScheduler.runTaskTimerAsynchronously(
                LimeyBukkitLoaderPlugin.LOADER,
                task,
                initialDelayTicks,
                periodTicks
        ));
    }

    @Override
    public void cancel(@NotNull GrimPlugin plugin) {
        bukkitScheduler.cancelTasks(LimeyBukkitLoaderPlugin.LOADER);
    }
}
