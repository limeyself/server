package ac.limey.limeyac.checks.impl.crash;

import ac.limey.limeyac.checks.CheckData;
import ac.limey.limeyac.checks.type.BlockBreakListener;
import ac.limey.limeyac.checks.type.BlockPlaceCheck;
import ac.limey.limeyac.checks.type.BlockPlaceListener;
import ac.limey.limeyac.checks.type.PacketReceiveListener;
import ac.limey.limeyac.player.LimeyPlayer;
import ac.limey.limeyac.utils.anticheat.update.BlockBreak;
import ac.limey.limeyac.utils.anticheat.update.BlockPlace;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientUseItem;

@CheckData(name = "CrashG", stableKey = "limey.crash.negative_sequence", description = "Sent negative sequence id")
public class CrashG extends BlockPlaceCheck implements PacketReceiveListener, BlockPlaceListener, BlockBreakListener {

    public CrashG(LimeyPlayer player) {
        super(player);
    }

    @Override
    public boolean isApplicable() {
        return player.getClientVersion().isNewerThanOrEquals(ClientVersion.V_1_19)
                && PacketEvents.getAPI().getServerManager().getVersion().isNewerThanOrEquals(ServerVersion.V_1_19);
    }

    @Override
    public void onPacketReceive(final PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.USE_ITEM) {
            WrapperPlayClientUseItem use = new WrapperPlayClientUseItem(event);
            if (use.getSequence() < 0) {
                flag();
                event.setCancelled(true);
                player.onPacketCancel();
            }
        }
    }

    @Override
    public void onBlockBreak(BlockBreak blockBreak) {
        if (blockBreak.sequence < 0) {
            flag();
            blockBreak.cancel();
        }
    }

    @Override
    public void onBlockPlace(BlockPlace place) {
        if (place.sequence < 0) {
            flag();
            place.resync();
        }
    }
}
