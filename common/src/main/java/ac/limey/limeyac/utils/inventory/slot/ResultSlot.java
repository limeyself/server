package ac.limey.limeyac.utils.inventory.slot;

import ac.limey.limeyac.player.LimeyPlayer;
import ac.limey.limeyac.utils.inventory.InventoryStorage;
import com.github.retrooper.packetevents.protocol.item.ItemStack;

public class ResultSlot extends Slot {

    public ResultSlot(InventoryStorage container, int slot) {
        super(container, slot);
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
        return false;
    }

    @Override
    public void onTake(LimeyPlayer player, ItemStack itemStack) {
        // Resync the player's inventory
    }
}
