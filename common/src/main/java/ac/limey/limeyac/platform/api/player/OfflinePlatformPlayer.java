package ac.limey.limeyac.platform.api.player;

import ac.grim.grimac.api.GrimIdentity;

public interface OfflinePlatformPlayer extends GrimIdentity {

    boolean isOnline();

    String getName();
}
