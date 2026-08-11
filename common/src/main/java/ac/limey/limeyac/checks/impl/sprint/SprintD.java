package ac.limey.limeyac.checks.impl.sprint;

import ac.limey.limeyac.checks.Check;
import ac.limey.limeyac.checks.CheckData;
import ac.limey.limeyac.checks.type.PacketReceiveListener;
import ac.limey.limeyac.checks.type.PostPredictionListener;
import ac.limey.limeyac.player.LimeyPlayer;
import ac.limey.limeyac.utils.anticheat.update.PredictionComplete;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientEntityAction;

import static com.github.retrooper.packetevents.protocol.potion.PotionTypes.BLINDNESS;

@CheckData(name = "SprintD", stableKey = "limey.sprint.blindness", description = "Started sprinting while having blindness", setback = 5, experimental = true)
public class SprintD extends Check implements PacketReceiveListener, PostPredictionListener {
    public boolean startedSprintingBeforeBlind = false;

    public SprintD(LimeyPlayer player) {
        super(player);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() == PacketType.Play.Client.ENTITY_ACTION) {
            if (new WrapperPlayClientEntityAction(event).getAction() == WrapperPlayClientEntityAction.Action.START_SPRINTING) {
                startedSprintingBeforeBlind = false;
            }
        }
    }

    @Override
    public void onPredictionComplete(final PredictionComplete predictionComplete) {
        if (player.compensatedEntities.self.hasPotionEffect(BLINDNESS) && !startedSprintingBeforeBlind) {
            if (player.isSprinting) {
                flagWithSetback();
            } else reward();
        }
    }
}
