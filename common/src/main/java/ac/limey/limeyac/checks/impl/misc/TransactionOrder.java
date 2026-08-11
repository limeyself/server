package ac.limey.limeyac.checks.impl.misc;

import ac.limey.limeyac.checks.Check;
import ac.limey.limeyac.checks.CheckData;
import ac.limey.limeyac.player.LimeyPlayer;

@CheckData(name = "TransactionOrder", stableKey = "limey.ping.invalid_transaction_order", description = "Sent transaction or ping responses in an invalid order")
public class TransactionOrder extends Check {
    public TransactionOrder(LimeyPlayer player) {
        super(player);
    }
}
