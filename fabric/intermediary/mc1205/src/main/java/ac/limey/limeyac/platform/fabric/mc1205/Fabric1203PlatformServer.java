package ac.limey.limeyac.platform.fabric.mc1205;

import ac.limey.limeyac.platform.api.sender.Sender;
import ac.limey.limeyac.platform.fabric.LimeyFabricIntermediaryLoaderPlugin;
import ac.limey.limeyac.platform.fabric.mc1194.Fabric1190PlatformServer;
import net.minecraft.commands.CommandSourceStack;

public class Fabric1203PlatformServer extends Fabric1190PlatformServer {

    @Override
    public double getTPS() {
        return Math.min(1000.0 / LimeyFabricIntermediaryLoaderPlugin.FABRIC_SERVER.getCurrentSmoothedTickTime(), LimeyFabricIntermediaryLoaderPlugin.FABRIC_SERVER.tickRateManager().tickrate());
    }

    @Override
    public void dispatchCommand(Sender sender, String command) {
        CommandSourceStack commandSource = LimeyFabricIntermediaryLoaderPlugin.LOADER.getFabricSenderFactory().unwrap(sender);
        LimeyFabricIntermediaryLoaderPlugin.FABRIC_SERVER.getCommands().performPrefixedCommand(commandSource, command);
    }
}
