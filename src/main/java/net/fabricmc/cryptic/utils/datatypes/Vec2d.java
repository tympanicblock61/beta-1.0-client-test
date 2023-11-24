package net.fabricmc.cryptic.utils.datatypes;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;


public class Vec2d {
    private static final Vec2dPool threadPool = new Vec2dPool(100, 1);
    public double x;
    public double y;

    public static Vec2d create(double x2, double y2) {
        return new Vec2d(x2, y2);
    }

    public static Vec2dPool threadPool() {
        return threadPool;
    }

    protected Vec2d(double x, double y) {
        if (x == -0.0) x = 0.0;
        if (y == -0.0) y = 0.0;

        this.x = x;
        this.y = y;
    }

    public Vec2d set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Environment(value= EnvType.CLIENT)
    public Vec2d interpolatePosition(Vec2d target, double alpha) {
        double interpolatedX = x + (target.x - x) * alpha;
        double interpolatedY = y + (target.y - y) * alpha;
        return Vec2d.threadPool().getOrCreate(interpolatedX, interpolatedY);
    }

    @Environment(value=EnvType.CLIENT)
    public Vec2d getOrCreate(Vec2d vec2d) {
        return Vec2d.threadPool().getOrCreate(vec2d.x - x, vec2d.y - y);
    }

    public Vec2d normalize() {
        double squared = MathHelper.sqrt(x * x + y * y);
        if (squared < 1.0E-4) {
            return Vec2d.threadPool().getOrCreate(0.0, 0.0);
        }
        return Vec2d.threadPool().getOrCreate(x / squared, y / squared);
    }

    public double dotProduct(Vec2d vec) {
        return x * vec.x + y * vec.y;
    }

    @Environment(value = EnvType.CLIENT)
    public Vec2d crossProductWith(Vec2d otherVector) {
        double resultX = x * otherVector.y - y * otherVector.x;
        double resultY = y * otherVector.x - x * otherVector.y;
        return Vec2d.threadPool().getOrCreate(resultX, resultY);
    }


    public Vec2d add(double d2, double e2) {
        return Vec2d.threadPool().getOrCreate(x + d2, y + e2);
    }

    public double distanceTo(Vec2d vec) {
        double d2 = vec.x - x;
        double d3 = vec.y - y;
        return MathHelper.sqrt(d2 * d2 + d3 * d3);
    }

    public double squaredDistanceTo(Vec2d vec) {
        double d2 = vec.x - x;
        double d3 = vec.y - y;
        return d2 * d2 + d3 * d3;
    }

    public double squaredDistanceTo(double x2, double y2) {
        double d2 = x2 - x;
        double d3 = y2 - y;
        return d2 * d2 + d3 * d3;
    }

    @Environment(value=EnvType.CLIENT)
    public Vec2d multiply(double amount) {
        return Vec2d.threadPool().getOrCreate(x * amount, y * amount);
    }

    public double length() {
        return MathHelper.sqrt(x * x + y * y);
    }

    public Vec2d lerpForX(Vec2d other, double x2) {
        double d2 = other.x - x;
        double d3 = other.y - y;
        if (d2 * d2 < (double)1.0E-7f) {
            return null;
        }
        double d5 = (x2 - x) / d2;
        if (d5 < 0.0 || d5 > 1.0) {
            return null;
        }
        return Vec2d.threadPool().getOrCreate(x + d2 * d5, y + d3 * d5);
    }

    public Vec2d lerpForY(Vec2d other, double y2) {
        double d2 = other.x - x;
        double d3 = other.y - y;
        if (d3 * d3 < (double)1.0E-7f) {
            return null;
        }
        double d5 = (y2 - y) / d3;
        if (d5 < 0.0 || d5 > 1.0) {
            return null;
        }
        return Vec2d.threadPool().getOrCreate(x + d2 * d5, y + d3 * d5);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Environment(value=EnvType.CLIENT)
    public Vec2d lerpTowards(Vec2d target, double alpha) {
        return Vec2d.threadPool().getOrCreate(x + (target.x - x) * alpha, y + (target.y - y) * alpha);
    }

    public void rotateAroundX(float angleX) {
        float cosX = MathHelper.cos(angleX);
        float sinX = MathHelper.sin(angleX);
        x = y * sinX + x * cosX;
        y = y * cosX - x * sinX;
    }

    public void rotateAroundY(float angleY) {
        float cosY = MathHelper.cos(angleY);
        float sinY = MathHelper.sin(angleY);
        x = x * cosY - y * sinY;
        y = x * sinY + y * cosY;
    }

}
