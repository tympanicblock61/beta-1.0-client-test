package net.fabricmc.cryptic.utils.datatypes;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Vec2i {
    private static final Vec2iPool threadPool = new Vec2iPool(100, 1);
    public int x;
    public int y;

    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull Vec2i create(int x2, int y2) {
        return new Vec2i(x2, y2);
    }

    public static Vec2iPool threadPool() {
        return threadPool;
    }

    protected Vec2i(int x, int y) {
        if (x == -0) x = 0;
        if (y == -0) y = 0;

        this.x = x;
        this.y = y;
    }

    public Vec2i(@NotNull Vec2d vec2d) {
        this(MathHelper.floor(vec2d.x), MathHelper.floor(vec2d.y));
    }

    protected Vec2i set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Environment(value= EnvType.CLIENT)
    public Vec2i interpolatePosition(@NotNull Vec2i target, int alpha) {
        int interpolatedX = x + (target.x - x) * alpha;
        int interpolatedY = y + (target.y - y) * alpha;
        return Vec2i.threadPool().getOrCreate(interpolatedX, interpolatedY);
    }

    @Environment(value=EnvType.CLIENT)
    public Vec2i getOrCreate(@NotNull Vec2i Vec2i) {
        return threadPool().getOrCreate(Vec2i.x - x, Vec2i.y - y);
    }

    public Vec2i normalize() {
        float squared = MathHelper.sqrt(x * x + y * y);
        if (squared < 1.0E-4) return threadPool().getOrCreate(0, 0);
        return threadPool().getOrCreate(MathHelper.floor( x / squared), MathHelper.floor(y / squared));
    }

    public int dotProduct(@NotNull Vec2i vec) {
        return x * vec.x + y * vec.y;
    }

    @Environment(value = EnvType.CLIENT)
    public Vec2i crossProductWith(@NotNull Vec2i otherVector) {
        int resultX = x * otherVector.y - y * otherVector.x;
        int resultY = y * otherVector.x - x * otherVector.y;
        return threadPool().getOrCreate(resultX, resultY);
    }


    public Vec2i add(int d2, int e2) {
        return threadPool().getOrCreate(x + d2, y + e2);
    }

    public int distanceTo(@NotNull Vec2i vec) {
        int d2 = vec.x - x;
        int d3 = vec.y - y;
        return MathHelper.floor(MathHelper.sqrt(d2 * d2 + d3 * d3));
    }

    public int squaredDistanceTo(@NotNull Vec2i vec) {
        int d2 = vec.x - x;
        int d3 = vec.y - y;
        return d2 * d2 + d3 * d3;
    }

    public int squaredDistanceTo(int x2, int y2) {
        int d2 = x2 - x;
        int d3 = y2 - y;
        return d2 * d2 + d3 * d3;
    }

    @Environment(value=EnvType.CLIENT)
    public Vec2i multiply(int amount) {
        return threadPool().getOrCreate(x * amount, y * amount);
    }

    public int length() {
        return MathHelper.floor(MathHelper.sqrt(x * x + y * y));
    }

    public Vec2i lerpForX(@NotNull Vec2i other, int x2) {
        int d2 = other.x - x;
        int d3 = other.y - y;
        if (d2 * d2 < 0) return null;
        int d5 = (x2 - x) / d2;
        if (d5 < 0.0 || d5 > 1.0) return null;
        return threadPool().getOrCreate(x + d2 * d5, y + d3 * d5);
    }

    public Vec2i lerpForY(@NotNull Vec2i other, int y2) {
        int d2 = other.x - x;
        int d3 = other.y - y;
        if (d3 * d3 < 0) return null;
        int d5 = (y2 - y) / d3;
        if (d5 < 0.0 || d5 > 1.0) return null;
        return threadPool().getOrCreate(x + d2 * d5, y + d3 * d5);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Environment(value=EnvType.CLIENT)
    public Vec2i lerpTowards(@NotNull Vec2i target, int alpha) {
        return threadPool().getOrCreate(x + (target.x - x) * alpha, y + (target.y - y) * alpha);
    }

    public void rotateAroundX(float angleX) {
        float cosX = MathHelper.cos(angleX);
        float sinX = MathHelper.sin(angleX);
        x = MathHelper.floor(y * sinX + x * cosX);
        y = MathHelper.floor(y * cosX - x * sinX);
    }

    public void rotateAroundY(float angleY) {
        float cosY = MathHelper.cos(angleY);
        float sinY = MathHelper.sin(angleY);
        x = MathHelper.floor(x * cosY - y * sinY);
        y = MathHelper.floor(x * sinY + y * cosY);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Vec3i) {
            Vec3i vec3i = (Vec3i)object;
            return vec3i.x == this.x && vec3i.y == this.y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.x * 8976890 + this.y * 981131;
    }
}
