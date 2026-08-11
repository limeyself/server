package ac.limey.limeyac.utils.item;

import ac.limey.limeyac.player.LimeyPlayer;
import ac.limey.limeyac.utils.latency.CompensatedWorld;
import com.github.retrooper.packetevents.protocol.item.ItemStack;
import com.github.retrooper.packetevents.protocol.player.InteractionHand;

public class AlwaysUseItem extends ItemBehaviour {

    public static final AlwaysUseItem INSTANCE = new AlwaysUseItem();

    @Override
    public boolean canUse(ItemStack item, CompensatedWorld world, LimeyPlayer player, InteractionHand hand) {
        return true;
    }

}
