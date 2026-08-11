package ac.limey.limeyac.checks.impl.scaffolding;

import ac.grim.grimac.api.storage.verbose.Verbose;
import ac.limey.limeyac.checks.CheckData;
import ac.limey.limeyac.checks.type.BlockPlaceCheck;
import ac.limey.limeyac.checks.type.BlockPlaceListener;
import ac.limey.limeyac.player.LimeyPlayer;
import ac.limey.limeyac.utils.anticheat.update.BlockPlace;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.manager.server.ServerVersion;

@CheckData(name = "InvalidPlaceB", stableKey = "limey.scaffolding.invalid_place_b", description = "Sent impossible block face id")
public class InvalidPlaceB extends BlockPlaceCheck implements BlockPlaceListener {
    private static final Verbose V = Verbose.of("direction={sint}");

    public InvalidPlaceB(LimeyPlayer player) {
        super(player);
    }

    @Override
    public void onBlockPlace(final BlockPlace place) {
        if (place.getFaceId() == 255 && PacketEvents.getAPI().getServerManager().getVersion().isOlderThanOrEquals(ServerVersion.V_1_8)) {
            return;
        }

        if (place.getFaceId() < 0 || place.getFaceId() > 5) {
            // ban
            int direction = place.getFaceId();
            if (flag(V.write(verbose()).sint(direction)) && shouldModifyPackets() && shouldCancel()) {
                place.resync();
            }
        }
    }
}
