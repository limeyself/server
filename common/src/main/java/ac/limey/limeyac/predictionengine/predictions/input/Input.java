package ac.limey.limeyac.predictionengine.predictions.input;

import ac.limey.limeyac.player.LimeyPlayer;
import ac.limey.limeyac.utils.math.Vector3dm;
import com.github.retrooper.packetevents.protocol.player.ClientVersion;

public interface Input {

    Vector3dm vector();

    Input normalize(LimeyPlayer player);

    static Input createInput(LimeyPlayer player, float sideways, float vertical, float forward) {
        if (player.getClientVersion().isNewerThanOrEquals(ClientVersion.V_1_14)) {
            return new DoubleInput(sideways, vertical, forward);
        } else {
            return new FloatInput(sideways, vertical, forward);
        }
    }

}
