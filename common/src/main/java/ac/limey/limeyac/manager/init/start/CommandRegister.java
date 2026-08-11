package ac.limey.limeyac.manager.init.start;

import ac.limey.limeyac.platform.api.command.CommandService;
import ac.limey.limeyac.utils.anticheat.LogUtil;

public record CommandRegister(CommandService service) implements StartableInitable {

    @Override
    public void start() {
        try {
            if (service != null) {
                service.registerCommands();
            }
        } catch (Throwable t) {
            // This is the ultimate safety net. If command registration fails, Limey keeps running.
            LogUtil.error("Failed to register commands! Limey will run without command support.", t);
        }
    }
}
