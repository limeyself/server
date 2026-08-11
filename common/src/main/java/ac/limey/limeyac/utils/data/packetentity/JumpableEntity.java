package ac.limey.limeyac.utils.data.packetentity;

import ac.limey.limeyac.player.LimeyPlayer;
import ac.limey.limeyac.utils.data.VectorData;

import java.util.Set;

public interface JumpableEntity {

    boolean isJumping();

    void setJumping(boolean jumping);

    float getJumpPower();

    void setJumpPower(float jumpPower);

    boolean canPlayerJump(LimeyPlayer player);

    boolean hasSaddle();

    void executeJump(LimeyPlayer player, Set<VectorData> possibleVectors);

}
