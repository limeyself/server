package ac.limey.limeyac.manager.player.features.types;

import ac.grim.grimac.api.config.ConfigManager;
import ac.grim.grimac.api.feature.FeatureState;
import ac.limey.limeyac.player.LimeyPlayer;

public interface LimeyFeature {
    String getName();

    void setState(LimeyPlayer player, ConfigManager config, FeatureState state);

    boolean isEnabled(LimeyPlayer player);

    boolean isEnabledInConfig(LimeyPlayer player, ConfigManager config);
}
