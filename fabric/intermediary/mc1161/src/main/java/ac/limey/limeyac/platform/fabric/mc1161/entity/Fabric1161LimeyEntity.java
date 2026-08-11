package ac.limey.limeyac.platform.fabric.mc1161.entity;

import ac.limey.limeyac.platform.fabric.entity.AbstractFabricLimeyEntity;
import ac.limey.limeyac.platform.fabric.utils.thread.FabricFutureUtil;
import ac.limey.limeyac.utils.math.Location;
import java.util.concurrent.CompletableFuture;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class Fabric1161LimeyEntity extends AbstractFabricLimeyEntity<Entity> {

    public Fabric1161LimeyEntity(Entity entity) {
        super(entity);
    }

    @Override
    public CompletableFuture<Boolean> teleportAsync(Location location) {
        return FabricFutureUtil.supplySync(() -> {
            if (entity.getCommandSenderWorld() instanceof ServerLevel) {
                entity.teleportToWithTicket(
                        location.getX(),
                        location.getY(),
                        location.getZ()
                );
                return true;
            }
            return false;
        });
    }

    @Override
    public boolean isDead() {
        return entity instanceof LivingEntity living ? living.isDeadOrDying() : entity.removed;
    }
}
