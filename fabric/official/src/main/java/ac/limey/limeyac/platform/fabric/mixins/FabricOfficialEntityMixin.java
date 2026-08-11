package ac.limey.limeyac.platform.fabric.mixins;

import ac.limey.limeyac.platform.fabric.inject.FabricEntityHandle;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;

import java.util.UUID;

@Mixin(Entity.class)
@Implements(@Interface(iface = FabricEntityHandle.class, prefix = "limey$"))
abstract class FabricOfficialEntityMixin {

    public UUID limey$fabricEntityUuid() {
        return ((Entity) (Object) this).getUUID();
    }

    public boolean limey$fabricEjectPassengers() {
        Entity entity = (Entity) (Object) this;
        if (entity.isVehicle()) {
            entity.ejectPassengers();
            return true;
        }
        return false;
    }

    public Object limey$fabricWorld() {
        return ((Entity) (Object) this).level;
    }

    public double limey$fabricPosX() {
        return ((Entity) (Object) this).getX();
    }

    public double limey$fabricPosY() {
        return ((Entity) (Object) this).getY();
    }

    public double limey$fabricPosZ() {
        return ((Entity) (Object) this).getZ();
    }

    public float limey$fabricYaw(float partialTick) {
        return ((Entity) (Object) this).getViewYRot(partialTick);
    }

    public float limey$fabricPitch(float partialTick) {
        return ((Entity) (Object) this).getViewXRot(partialTick);
    }
}
