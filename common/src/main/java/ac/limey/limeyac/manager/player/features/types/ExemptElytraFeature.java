package ac.limey.limeyac.manager.player.features.types;

import ac.grim.grimac.api.config.ConfigManager;
import ac.grim.grimac.api.feature.FeatureState;
import ac.limey.limeyac.player.LimeyPlayer;

public class ExemptElytraFeature implements LimeyFeature {

    @Override
    public String getName() {
        return "ExemptElytra";
    }

    @Override
    public void setState(LimeyPlayer player, ConfigManager config, FeatureState state) {
        switch (state) {
            case ENABLED -> player.setExemptElytra(true);
            case DISABLED -> player.setExemptElytra(false);
            default -> player.setExemptElytra(isEnabledInConfig(player, config));
        }
    }

    @Override
    public boolean isEnabled(LimeyPlayer player) {
        return player.isExemptElytra();
    }

    @Override
    public boolean isEnabledInConfig(LimeyPlayer player, ConfigManager config) {
        return config.getBooleanElse("exempt-elytra", false);
    }

}
