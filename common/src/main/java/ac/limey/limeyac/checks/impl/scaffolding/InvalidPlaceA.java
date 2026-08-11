package ac.limey.limeyac.checks.impl.scaffolding;

import ac.limey.limeyac.checks.CheckData;
import ac.limey.limeyac.checks.type.BlockPlaceCheck;
import ac.limey.limeyac.checks.type.BlockPlaceListener;
import ac.limey.limeyac.player.LimeyPlayer;
import ac.limey.limeyac.utils.anticheat.update.BlockPlace;
import com.github.retrooper.packetevents.util.Vector3f;

@CheckData(name = "InvalidPlaceA", stableKey = "limey.scaffolding.invalid_place_a", description = "Sent invalid cursor position")
public class InvalidPlaceA extends BlockPlaceCheck implements BlockPlaceListener {
    public InvalidPlaceA(LimeyPlayer player) {
        super(player);
    }

    @Override
    public void onBlockPlace(final BlockPlace place) {
        Vector3f cursor = place.cursor;
        if (cursor == null) return;
        if (!Float.isFinite(cursor.x) || !Float.isFinite(cursor.y) || !Float.isFinite(cursor.z)) {
            if (flag() && shouldModifyPackets() && shouldCancel()) {
                place.resync();
            }
        }
    }
}
