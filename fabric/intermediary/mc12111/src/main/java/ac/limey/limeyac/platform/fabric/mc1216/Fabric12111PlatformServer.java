package ac.limey.limeyac.platform.fabric.mc1216;

import ac.limey.limeyac.platform.api.sender.Sender;
import ac.limey.limeyac.platform.fabric.LimeyFabricIntermediaryLoaderPlugin;
import ac.limey.limeyac.platform.fabric.mc1205.Fabric1203PlatformServer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.PermissionLevel;

public class Fabric12111PlatformServer extends Fabric1203PlatformServer {

    @Override
    public int getOperatorPermissionLevel() {
        return LimeyFabricIntermediaryLoaderPlugin.FABRIC_SERVER.operatorUserPermissions().level().id();
    }

    @Override
    public boolean hasPermission(Sender sender, int level) {
        CommandSourceStack stack = (CommandSourceStack) sender;
        return stack.permissions().hasPermission(
                new Permission.HasCommandLevel(PermissionLevel.byId(level))
        );
    }
}
