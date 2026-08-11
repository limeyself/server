package ac.limey.limeyac.manager.init.stop;

import ac.limey.limeyac.manager.init.Initable;

public interface StoppableInitable extends Initable {
    void stop();
}
