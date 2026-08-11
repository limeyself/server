package ac.limey.limeyac.platform.fabric.utils.message;

import ac.limey.limeyac.platform.api.sender.Sender;

public interface IFabricMessageUtil {
    Object textLiteral(String message);

    void sendMessage(Sender target, Object message, boolean overlay);
}
