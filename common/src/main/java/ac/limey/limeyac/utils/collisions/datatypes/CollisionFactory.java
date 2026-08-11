package ac.limey.limeyac.utils.collisions.datatypes;

import ac.limey.limeyac.player.LimeyPlayer;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import com.github.retrooper.packetevents.protocol.world.states.WrappedBlockState;

public interface CollisionFactory {
    CollisionBox fetch(LimeyPlayer player, ClientVersion version, WrappedBlockState block, int x, int y, int z);
}
