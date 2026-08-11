package ac.limey.limeyac.platform.fabric.mixins;

import ac.limey.limeyac.LimeyAPI;
import ac.limey.limeyac.platform.api.player.PlatformPlayer;
import ac.limey.limeyac.platform.api.sender.Sender;
import ac.limey.limeyac.platform.fabric.LimeyFabricIntermediaryLoaderPlugin;
import ac.limey.limeyac.platform.fabric.sender.FabricIntermediarySenderFactory;
import net.kyori.adventure.text.Component;
import net.minecraft.commands.CommandSourceStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.UUID;

@Mixin(CommandSourceStack.class)
@Implements(@Interface(iface = Sender.class, prefix = "limey$"))
abstract class FabricIntermediaryCommandSourceStackMixin {

    @Unique
    private CommandSourceStack limey$self() {
        return (CommandSourceStack) (Object) this;
    }

    @Unique
    private FabricIntermediarySenderFactory limey$factory() {
        return LimeyFabricIntermediaryLoaderPlugin.LOADER.getFabricSenderFactory();
    }

    public UUID limey$getUniqueId() {
        return limey$factory().getUniqueId(limey$self());
    }

    public String limey$getName() {
        return limey$factory().getName(limey$self());
    }

    public void limey$sendMessage(String message) {
        limey$factory().sendMessage(limey$self(), message);
    }

    public void limey$sendMessage(Component message) {
        limey$factory().sendMessage(limey$self(), message);
    }

    public boolean limey$hasPermission(String permission) {
        return limey$factory().hasPermission(limey$self(), permission);
    }

    public boolean limey$hasPermission(String permission, boolean defaultIfUnset) {
        return limey$factory().hasPermission(limey$self(), permission, defaultIfUnset);
    }

    public void limey$performCommand(String commandLine) {
        limey$factory().performCommand(limey$self(), commandLine);
    }

    public boolean limey$isConsole() {
        return limey$factory().isConsole(limey$self());
    }

    public Object limey$getNativeSender() {
        return limey$self();
    }

    public @Nullable PlatformPlayer limey$getPlatformPlayer() {
        return LimeyAPI.INSTANCE.getPlatformPlayerFactory().getFromUUID(limey$getUniqueId());
    }
}
