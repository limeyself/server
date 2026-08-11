package ac.limey.limeyac.platform.fabric.mixins;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;

import java.util.UUID;

@Mixin(ServerPlayer.class)
@Implements(@Interface(iface = ac.limey.limeyac.platform.fabric.inject.FabricServerPlayerHandle.class, prefix = "limey$"))
abstract class FabricIntermediaryServerPlayerMixin {

    public boolean limey$isSneaking() {
        return ((ServerPlayer) (Object) this).isShiftKeyDown();
    }

    public void limey$setSneaking(boolean sneaking) {
        ((ServerPlayer) (Object) this).setShiftKeyDown(sneaking);
    }

    public boolean limey$isDead() {
        return ((ServerPlayer) (Object) this).isDeadOrDying();
    }

    public void limey$sendSystemText(Object nativeComponent) {
        ((ServerPlayer) (Object) this).displayClientMessage((Component) nativeComponent, false);
    }

    public boolean limey$isDisconnected() {
        return ((ServerPlayer) (Object) this).hasDisconnected();
    }

    public String limey$usernameString() {
        return ((ServerPlayer) (Object) this).getName().getString();
    }

    public void limey$broadcastInventoryChanges() {
        ((ServerPlayer) (Object) this).containerMenu.broadcastChanges();
    }

    public void limey$stopUsingItem() {
        ((ServerPlayer) (Object) this).stopUsingItem();
    }

    public boolean limey$isUsingItem() {
        return ((ServerPlayer) (Object) this).isUsingItem();
    }

    public double limey$posX() {
        return ((ServerPlayer) (Object) this).getX();
    }

    public double limey$posY() {
        return ((ServerPlayer) (Object) this).getY();
    }

    public double limey$posZ() {
        return ((ServerPlayer) (Object) this).getZ();
    }

    public UUID limey$uuid() {
        return ((ServerPlayer) (Object) this).getUUID();
    }

    public Object limey$vehicleEntity() {
        return ((ServerPlayer) (Object) this).getVehicle();
    }

    public Object limey$gameMode() {
        return ((ServerPlayer) (Object) this).gameMode.getGameModeForPlayer();
    }

    public Object limey$heldItemStack() {
        return ((ServerPlayer) (Object) this).inventory.getSelected();
    }

    public Object limey$inventoryItemAt(int slot) {
        return ((ServerPlayer) (Object) this).inventory.getItem(slot);
    }

    public Object limey$usedItemHand() {
        return ((ServerPlayer) (Object) this).getUsedItemHand();
    }

    public int limey$inventorySlotCount() {
        return ((ServerPlayer) (Object) this).inventory.getContainerSize();
    }
}
