package ac.limey.limeyac.platform.fabric.mc261;

import ac.limey.limeyac.platform.fabric.entity.AbstractFabricLimeyEntity;
import ac.limey.limeyac.platform.fabric.utils.thread.FabricFutureUtil;
import ac.limey.limeyac.utils.math.Location;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Relative;

import java.util.EnumSet;
import java.util.concurrent.CompletableFuture;

public class Fabric261LimeyEntity extends AbstractFabricLimeyEntity<Entity> {
    public Fabric261LimeyEntity(Entity entity) {
        super(entity);
    }

    @Override
    public boolean isDead() {
        return this.entity instanceof LivingEntity living ? living.isDeadOrDying() : this.entity.isRemoved();
    }

    @Override
    public CompletableFuture<Boolean> teleportAsync(Location location) {
        return FabricFutureUtil.supplySync(() -> {
            this.entity.teleportTo(
                    (ServerLevel) location.getWorld(),
                    location.getX(),
                    location.getY(),
                    location.getZ(),
                    EnumSet.noneOf(Relative.class),
                    location.getYaw(),
                    location.getPitch(),
                    true
            );
            return true;
        });
    }
}
