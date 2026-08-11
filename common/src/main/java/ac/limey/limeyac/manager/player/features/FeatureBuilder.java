package ac.limey.limeyac.manager.player.features;

import ac.limey.limeyac.manager.player.features.types.LimeyFeature;
import ac.limey.limeyac.utils.anticheat.LogUtil;
import com.google.common.collect.ImmutableMap;

import java.util.regex.Pattern;

public class FeatureBuilder {

    private static final Pattern VALID = Pattern.compile("[a-zA-Z0-9_]{1,64}");
    private final ImmutableMap.Builder<String, LimeyFeature> mapBuilder = ImmutableMap.builder();

    public <T extends LimeyFeature> void register(T feature) {
        if (!VALID.matcher(feature.getName()).matches()) {
            LogUtil.error("Invalid feature name: " + feature.getName());
            return;
        }
        mapBuilder.put(feature.getName(), feature);
    }

    public ImmutableMap<String, LimeyFeature> buildMap() {
        return mapBuilder.build();
    }

}
