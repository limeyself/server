package ac.limey.limeyac.platform.fabric.mc1171.entity;

import ac.limey.limeyac.platform.fabric.mc1161.entity.Fabric1161LimeyEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class Fabric1170LimeyEntity extends Fabric1161LimeyEntity {

    public Fabric1170LimeyEntity(Entity entity) {
        super(entity);
    }

    @Override
    public boolean isDead() {
        return this.entity instanceof LivingEntity living ? living.isDeadOrDying() : this.entity.isRemoved();
    }
}
