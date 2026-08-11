package ac.limey.limeyac.platform.fabric.initables;

import ac.limey.limeyac.LimeyAPI;
import ac.limey.limeyac.manager.init.start.StartableInitable;
import ac.limey.limeyac.manager.init.stop.StoppableInitable;
import ac.limey.limeyac.platform.fabric.utils.metrics.MetricsFabric;
import ac.limey.limeyac.utils.anticheat.Constants;

public class FabricBStats implements StartableInitable, StoppableInitable {

    private MetricsFabric metricsFabric;

    @Override
    public void start() {
        try {
            metricsFabric = new MetricsFabric(LimeyAPI.INSTANCE.getLimeyPlugin(), Constants.BSTATS_PLUGIN_ID);
        } catch (Exception ignored) {}
    }

    @Override
    public void stop() {
        if (metricsFabric != null)
            metricsFabric.shutdown();
    }
}
