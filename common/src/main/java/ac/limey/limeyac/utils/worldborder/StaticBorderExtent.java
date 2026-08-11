package ac.limey.limeyac.utils.worldborder;

import ac.limey.limeyac.utils.math.LimeyMath;

public record StaticBorderExtent(double size) implements BorderExtent {

    @Override
    public double getMinX(double centerX, double absoluteMaxSize) {
        return LimeyMath.clamp(centerX - size / 2.0, -absoluteMaxSize, absoluteMaxSize);
    }

    @Override
    public double getMaxX(double centerX, double absoluteMaxSize) {
        return LimeyMath.clamp(centerX + size / 2.0, -absoluteMaxSize, absoluteMaxSize);
    }

    @Override
    public double getMinZ(double centerZ, double absoluteMaxSize) {
        return LimeyMath.clamp(centerZ - size / 2.0, -absoluteMaxSize, absoluteMaxSize);
    }

    @Override
    public double getMaxZ(double centerZ, double absoluteMaxSize) {
        return LimeyMath.clamp(centerZ + size / 2.0, -absoluteMaxSize, absoluteMaxSize);
    }

    @Override
    public BorderExtent tick() {
        return this;
    }

    @Override
    public BorderExtent update() {
        return this;
    }

}
