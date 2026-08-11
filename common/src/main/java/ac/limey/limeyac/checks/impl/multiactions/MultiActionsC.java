package ac.limey.limeyac.checks.impl.multiactions;

import ac.grim.grimac.api.storage.verbose.Verbose;
import ac.limey.limeyac.checks.Check;
import ac.limey.limeyac.checks.CheckData;
import ac.limey.limeyac.checks.type.PacketReceiveListener;
import ac.limey.limeyac.player.LimeyPlayer;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@CheckData(name = "MultiActionsC", stableKey = "limey.multiactions.inventory_click_while_moving", description = "Clicked in inventory while moving")
public class MultiActionsC extends Check implements PacketReceiveListener {
    private static final Verbose V = Verbose.of("sprinting={bool}, sneaking={bool}, input={bool}");

    public MultiActionsC(LimeyPlayer player) {
        super(player);
    }

    @Contract(pure = true)
    public static boolean isVerboseSprinting(@NotNull LimeyPlayer player) {
        return player.isSprinting && (!player.isSwimming || !player.clientClaimsLastOnGround);
    }

    @Contract(pure = true)
    public static boolean isVerboseSneaking(@NotNull LimeyPlayer player) {
        return player.isSneaking && player.getClientVersion().isOlderThan(ClientVersion.V_1_15);
    }

    @Contract(pure = true)
    public static boolean isVerboseInput(@NotNull LimeyPlayer player) {
        return player.supportsEndTick() && player.packetStateData.knownInput.moving();
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() != PacketType.Play.Client.CLICK_WINDOW) return;
        if (player.serverOpenedInventoryThisTick) return;

        boolean sprinting = isVerboseSprinting(player);
        boolean sneaking = isVerboseSneaking(player);
        boolean input = isVerboseInput(player);
        if (!sprinting && !sneaking && !input) return;

        if (flag(V.write(verbose()).bool(sprinting).bool(sneaking).bool(input)) && shouldModifyPackets()) {
            event.setCancelled(true);
            player.onPacketCancel();
        }
    }
}
