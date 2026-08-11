package ac.limey.limeyac.platform.api.manager;

import ac.limey.limeyac.platform.api.player.PlatformPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MessagePlaceHolderManager {
    @NotNull
    String replacePlaceholders(@Nullable PlatformPlayer player, @NotNull String string);
}
