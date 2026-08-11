package ac.limey.limeyac.platform.fabric.mixins;

import ac.limey.limeyac.platform.api.world.PlatformChunk;
import ac.limey.limeyac.platform.api.world.PlatformWorld;
import ac.limey.limeyac.platform.fabric.LimeyFabricOfficialLoaderPlugin;
import ac.limey.limeyac.platform.fabric.utils.world.FabricOfficialLevelChunkUtil;
import com.github.retrooper.packetevents.protocol.world.states.WrappedBlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;

import java.util.UUID;

@Mixin(Level.class)
@Implements(@Interface(iface = PlatformWorld.class, prefix = "limey$"))
abstract class FabricOfficialLevelMixin implements LevelAccessor {

    @Shadow
    public abstract ResourceKey<Level> dimension();

    public boolean limey$isChunkLoaded(int chunkX, int chunkZ) {
        return FabricOfficialLevelChunkUtil.hasChunkAt((Level) (Object) this, chunkX, chunkZ);
    }

    public WrappedBlockState limey$getBlockAt(int x, int y, int z) {
        return WrappedBlockState.getByGlobalId(
                Block.getId(getBlockState(new BlockPos(x, y, z)))
        );
    }

    public String limey$getName() {
        return this.dimension().identifier().toString();
    }

    public @Nullable UUID limey$getUID() {
        return null;
    }

    public PlatformChunk limey$getChunkAt(int currChunkX, int currChunkZ) {
        return (PlatformChunk) getChunk(currChunkX, currChunkZ);
    }

    public boolean limey$isLoaded() {
        return LimeyFabricOfficialLoaderPlugin.FABRIC_SERVER.getLevel(this.dimension()) != null;
    }
}
