package ac.limey.limeyac.platform.fabric.mc1194;

import ac.limey.limeyac.platform.api.sender.Sender;
import ac.limey.limeyac.platform.fabric.LimeyFabricIntermediaryLoaderPlugin;
import ac.limey.limeyac.platform.fabric.mc1171.Fabric1171PlatformServer;
import net.minecraft.commands.CommandSourceStack;

public class Fabric1190PlatformServer extends Fabric1171PlatformServer {
    @Override
    public void dispatchCommand(Sender sender, String command) {
        CommandSourceStack commandSource = LimeyFabricIntermediaryLoaderPlugin.LOADER.getFabricSenderFactory().unwrap(sender);
        LimeyFabricIntermediaryLoaderPlugin.FABRIC_SERVER.getCommands().performPrefixedCommand(commandSource, command);
    }
}
