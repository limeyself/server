package ac.limey.limeyac.platform.fabric.mixins;

import ac.limey.limeyac.platform.api.world.PlatformChunk;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LevelChunk.class)
@Implements(@Interface(iface = PlatformChunk.class, prefix = "limey$"))
abstract class FabricOfficialLevelChunkMixin {
    @Unique
    private static final BlockPos.MutableBlockPos limey$sharedPos = new BlockPos.MutableBlockPos();

    public int limey$getBlockID(int x, int y, int z) {
        LevelChunk chunk = (LevelChunk) (Object) this;
        limey$sharedPos.set(chunk.getPos().getMinBlockX() + x, y, chunk.getPos().getMinBlockZ() + z);
        return Block.getId(chunk.getBlockState(limey$sharedPos));
    }
}
