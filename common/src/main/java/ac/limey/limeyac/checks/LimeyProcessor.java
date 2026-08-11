package ac.limey.limeyac.checks;

import ac.limey.limeyac.LimeyAPI;
import ac.grim.grimac.api.AbstractProcessor;
import ac.grim.grimac.api.config.ConfigReloadable;
import ac.limey.limeyac.utils.common.ConfigReloadObserver;

public abstract class LimeyProcessor implements AbstractProcessor, ConfigReloadable, ConfigReloadObserver {

    // Not everything has to be a check for it to process packets & be configurable

    @Override
    public void reload() {
        reload(LimeyAPI.INSTANCE.getConfigManager().getConfig());
    }

}
