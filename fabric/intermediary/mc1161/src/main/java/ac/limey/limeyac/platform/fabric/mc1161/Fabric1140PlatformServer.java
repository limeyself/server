package ac.limey.limeyac.platform.fabric.mc1161;

import ac.limey.limeyac.platform.api.sender.Sender;
import ac.limey.limeyac.platform.fabric.AbstractFabricPlatformServer;
import ac.limey.limeyac.platform.fabric.LimeyFabricIntermediaryLoaderPlugin;
import ac.limey.limeyac.platform.fabric.player.FabricOfflineProfile;
import com.mojang.authlib.GameProfile;
import net.minecraft.commands.CommandSourceStack;
import org.jetbrains.annotations.Nullable;

public class Fabric1140PlatformServer extends AbstractFabricPlatformServer {

    @Override
    public int getOperatorPermissionLevel() {
        return LimeyFabricIntermediaryLoaderPlugin.FABRIC_SERVER.getOperatorUserPermissionLevel();
    }

    @Override
    public boolean hasPermission(Sender sender, int level) {
        return ((CommandSourceStack) sender).hasPermission(level);
    }

    @Override
    public void dispatchCommand(Sender sender, String command) {
        CommandSourceStack commandSource = LimeyFabricIntermediaryLoaderPlugin.LOADER.getFabricSenderFactory().unwrap(sender);
        LimeyFabricIntermediaryLoaderPlugin.FABRIC_SERVER.getCommands().performCommand(commandSource, command);
    }

    @Override
    public double getTPS() {
        return Math.min(1000.0 / LimeyFabricIntermediaryLoaderPlugin.FABRIC_SERVER.getAverageTickTime(), 20.0);
    }

    @Override
    public @Nullable FabricOfflineProfile getProfileByName(String name) {
        GameProfile profile = LimeyFabricIntermediaryLoaderPlugin.FABRIC_SERVER.getProfileCache().get(name);
        return profile != null ? new FabricOfflineProfile(profile.getId(), profile.getName()) : null;
    }
}
