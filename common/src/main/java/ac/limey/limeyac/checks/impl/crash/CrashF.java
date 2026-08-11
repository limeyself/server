package ac.limey.limeyac.checks.impl.crash;

import ac.grim.grimac.api.storage.verbose.Verbose;
import ac.limey.limeyac.checks.Check;
import ac.limey.limeyac.checks.CheckData;
import ac.limey.limeyac.checks.impl.verbose.VerboseCodecs;
import ac.limey.limeyac.checks.type.PacketReceiveListener;
import ac.limey.limeyac.player.LimeyPlayer;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientClickWindow.WindowClickType;

@CheckData(name = "CrashF", stableKey = "limey.crash.button_crash", description = "Sent an inventory click with an invalid button or slot value")
public class CrashF extends Check implements PacketReceiveListener {
    private static final Verbose V =
            Verbose.of("clickType={clicktype}, button={sint}[, slot={sint}]");

    public CrashF(LimeyPlayer playerData) {
        super(playerData);
    }

    @Override
    public void onPacketReceive(final PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.CLICK_WINDOW) {
            WrapperPlayClientClickWindow click = new WrapperPlayClientClickWindow(event);
            WindowClickType clickType = click.getWindowClickType();
            int button = click.getButton();
            int windowId = click.getWindowId();
            int slot = click.getSlot();

            if ((clickType == WindowClickType.QUICK_MOVE || clickType == WindowClickType.SWAP) && windowId >= 0 && button < 0) {
                int clickTypeId = VerboseCodecs.enumId(clickType);
                if (flag(V.write(verbose()).uint(clickTypeId).sint(button).bool(false).sint(0))) {
                    event.setCancelled(true);
                    player.onPacketCancel();
                }
            } else if (windowId >= 0 && clickType == WindowClickType.SWAP && slot < 0) {
                int clickTypeId = VerboseCodecs.enumId(clickType);
                if (flag(V.write(verbose()).uint(clickTypeId).sint(button).bool(true).sint(slot))) {
                    event.setCancelled(true);
                    player.onPacketCancel();
                }
            }
        }
    }
}
