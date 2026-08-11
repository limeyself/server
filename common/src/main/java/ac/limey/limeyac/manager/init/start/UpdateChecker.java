package ac.limey.limeyac.manager.init.start;

import ac.limey.limeyac.LimeyAPI;
import ac.limey.limeyac.command.commands.LimeyVersion;

public class UpdateChecker implements StartableInitable {
    @Override
    public void start() {
        if (LimeyAPI.INSTANCE.getConfigManager().getConfig().getBooleanElse("check-for-updates", true)) {
            LimeyVersion.checkForUpdatesAsync(LimeyAPI.INSTANCE.getPlatformServer().getConsoleSender());
        }
    }
}
