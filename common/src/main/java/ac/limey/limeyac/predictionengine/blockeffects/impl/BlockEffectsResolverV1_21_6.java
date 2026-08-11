package ac.limey.limeyac.predictionengine.blockeffects.impl;

import ac.limey.limeyac.player.LimeyPlayer;
import ac.limey.limeyac.predictionengine.blockeffects.BlockCollisions;
import ac.limey.limeyac.predictionengine.blockeffects.BlockEffectsResolver;
import ac.limey.limeyac.predictionengine.blockeffects.BlockStepVisitor;
import ac.limey.limeyac.utils.collisions.datatypes.SimpleCollisionBox;
import ac.limey.limeyac.utils.math.LimeyMath;
import ac.limey.limeyac.utils.math.Vector3dm;
import ac.limey.limeyac.utils.nmsutil.Collisions;
import ac.limey.limeyac.utils.nmsutil.GetBoundingBox;
import com.github.retrooper.packetevents.protocol.world.states.WrappedBlockState;
import com.github.retrooper.packetevents.protocol.world.states.type.StateType;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.util.Vector3i;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;

import java.util.List;
import java.util.Optional;

// 1.21.6-1.21.8
public class BlockEffectsResolverV1_21_6 implements BlockEffectsResolver {

    public static final BlockEffectsResolver INSTANCE = new BlockEffectsResolverV1_21_6();

    @Override
    public void applyEffectsFromBlocks(LimeyPlayer player, Vector3dm clientVelocity, boolean onlyApplyVelocity, List<LimeyPlayer.Movement> movements) {
        LongSet visitedBlocks = player.visitedBlocks;

        for (LimeyPlayer.Movement movement : movements) {
            Vector3d from = movement.from();
            Vector3d to = movement.to().subtract(movement.from());
            if (movement.axisIndependant() && to.lengthSquared() > 0.0) {
                for (Collisions.Axis axis : BlockCollisions.axisStepOrder(to)) {
                    double value = axis.get(to);
                    if (value != 0.0) {
                        Vector3d vector = BlockCollisions.relative(from, axis.getPositive(), value);
                        checkInsideBlocks(player, clientVelocity, onlyApplyVelocity, from, vector, visitedBlocks);
                        from = vector;
                    }
                }
            } else {
                checkInsideBlocks(player, clientVelocity, onlyApplyVelocity, movement.from(), movement.to(), visitedBlocks);
            }
        }

        visitedBlocks.clear();
    }

    private static void checkInsideBlocks(LimeyPlayer player, Vector3dm clientVelocity, boolean onlyApplyVelocity, Vector3d from, Vector3d to, LongSet visitedBlocks) {
        SimpleCollisionBox boundingBox = GetBoundingBox.getCollisionBoxForPlayer(player, to.x, to.y, to.z).expand(-1.0E-5F);
        forEachBlockIntersectedBetween(from, to, boundingBox, (blockPos, i) -> {
            WrappedBlockState blockState = player.compensatedWorld.getBlock(blockPos);
            StateType blockType = blockState.getType();

            if (blockType.isAir()) {
                return true;
            }

            if (visitedBlocks.add(LimeyMath.asLong(blockPos))) {
                Collisions.onInsideBlock(player, clientVelocity, onlyApplyVelocity, blockType, blockState, blockPos.x, blockPos.y, blockPos.z, true);
            }

            return true;
        });
    }

    private static boolean forEachBlockIntersectedBetween(Vector3d start, Vector3d end, SimpleCollisionBox boundingBox, BlockStepVisitor blockStepVisitor) {
        Vector3d direction = end.subtract(start);
        if (direction.lengthSquared() < LimeyMath.square(0.99999F)) {
            for (Vector3i blockPos : SimpleCollisionBox.betweenClosed(boundingBox)) {
                if (!blockStepVisitor.visit(blockPos, 0)) {
                    return false;
                }
            }

            return true;
        } else {
            LongSet alreadyVisited = new LongOpenHashSet();
            Vector3d boxMinPosition = boundingBox.min().toVector3d();
            Vector3d subtractedMinPosition = boxMinPosition.subtract(direction);

            int iterationCount = addCollisionsAlongTravel(alreadyVisited, subtractedMinPosition, boxMinPosition, boundingBox, blockStepVisitor);
            if (iterationCount < 0) {
                return false;
            } else {
                for (Vector3i blockPos : SimpleCollisionBox.betweenClosed(boundingBox)) {
                    if (!alreadyVisited.contains(LimeyMath.asLong(blockPos)) && !blockStepVisitor.visit(blockPos, iterationCount + 1)) {
                        return false;
                    }
                }

                return true;
            }
        }
    }

    private static int addCollisionsAlongTravel(LongSet alreadyVisited, Vector3d start, Vector3d end, SimpleCollisionBox boundingBox, BlockStepVisitor blockStepVisitor) {
        Vector3d direction = end.subtract(start);
        int currentX = LimeyMath.floor(start.x);
        int currentY = LimeyMath.floor(start.y);
        int currentZ = LimeyMath.floor(start.z);
        int stepX = LimeyMath.sign(direction.x);
        int stepY = LimeyMath.sign(direction.y);
        int stepZ = LimeyMath.sign(direction.z);
        double tMaxX = stepX == 0 ? Double.MAX_VALUE : stepX / direction.x;
        double tMaxY = stepY == 0 ? Double.MAX_VALUE : stepY / direction.y;
        double tMaxZ = stepZ == 0 ? Double.MAX_VALUE : stepZ / direction.z;
        double tDeltaX = tMaxX * (stepX > 0 ? 1.0 - LimeyMath.frac(start.x) : LimeyMath.frac(start.x));
        double tDeltaY = tMaxY * (stepY > 0 ? 1.0 - LimeyMath.frac(start.y) : LimeyMath.frac(start.y));
        double tDeltaZ = tMaxZ * (stepZ > 0 ? 1.0 - LimeyMath.frac(start.z) : LimeyMath.frac(start.z));
        int iterationCount = 0;

        while (tDeltaX <= 1.0 || tDeltaY <= 1.0 || tDeltaZ <= 1.0) {
            if (tDeltaX < tDeltaY) {
                if (tDeltaX < tDeltaZ) {
                    currentX += stepX;
                    tDeltaX += tMaxX;
                } else {
                    currentZ += stepZ;
                    tDeltaZ += tMaxZ;
                }
            } else if (tDeltaY < tDeltaZ) {
                currentY += stepY;
                tDeltaY += tMaxY;
            } else {
                currentZ += stepZ;
                tDeltaZ += tMaxZ;
            }

            if (iterationCount++ > 16) {
                break;
            }

            Optional<Vector3d> collisionPoint = BlockCollisions.clip(currentX, currentY, currentZ, currentX + 1, currentY + 1, currentZ + 1, start, end);
            if (collisionPoint.isPresent()) {
                Vector3d collisionVec = collisionPoint.get();
                double clampedX = LimeyMath.clamp(collisionVec.x, currentX + 1.0E-5F, currentX + 1.0 - 1.0E-5F);
                double clampedY = LimeyMath.clamp(collisionVec.y, currentY + 1.0E-5F, currentY + 1.0 - 1.0E-5F);
                double clampedZ = LimeyMath.clamp(collisionVec.z, currentZ + 1.0E-5F, currentZ + 1.0 - 1.0E-5F);
                int endX = LimeyMath.floor(clampedX + boundingBox.getXSize());
                int endY = LimeyMath.floor(clampedY + boundingBox.getYSize());
                int endZ = LimeyMath.floor(clampedZ + boundingBox.getZSize());

                for (int x = currentX; x <= endX; x++) {
                    for (int y = currentY; y <= endY; y++) {
                        for (int z = currentZ; z <= endZ; z++) {
                            if (alreadyVisited.add(LimeyMath.asLong(x, y, z)) && !blockStepVisitor.visit(new Vector3i(x, y, z), iterationCount)) {
                                return -1;
                            }
                        }
                    }
                }
            }
        }

        return iterationCount;
    }

}
