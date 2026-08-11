package ac.limey.limeyac.checks.impl.vehicle;

import ac.limey.limeyac.checks.Check;
import ac.limey.limeyac.checks.CheckData;
import ac.limey.limeyac.player.LimeyPlayer;

@CheckData(name = "VehicleC", stableKey = "limey.vehicle.vehicle_control", description = "Moved a vehicle in a way that did not match predicted vehicle control")
public class VehicleC extends Check {
    public VehicleC(LimeyPlayer player) {
        super(player);
    }
}
