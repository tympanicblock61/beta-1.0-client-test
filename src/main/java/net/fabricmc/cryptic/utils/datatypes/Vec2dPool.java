package net.fabricmc.cryptic.utils.datatypes;

import java.util.ArrayList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class Vec2dPool {
    private final int cleanInterval;
    private final int cleanAmount;
    private final List<Vec2d> pool = new ArrayList<>();
    private int index = 0;
    private int highestIndex = 0;
    private int tick = 0;

    public Vec2dPool(int i2, int j2) {
        cleanInterval = i2;
        cleanAmount = j2;
    }

    public Vec2d getOrCreate(double x, double y) {
        Vec2d vec2d;
        if (index >= pool.size()) {
            vec2d = new Vec2d(x, y);
            pool.add(vec2d);
        } else {
            vec2d = pool.get(index);
            vec2d.set(x, y);
        }
        ++index;
        return vec2d;
    }

    public void tick() {
        if (index > highestIndex) {
            highestIndex = index;
        }
        if (tick++ == cleanInterval) {
            int n2 = Math.max(highestIndex, pool.size() - cleanAmount);
            while (pool.size() > n2) {
                pool.remove(n2);
            }
            highestIndex = 0;
            tick = 0;
        }
        index = 0;
    }

    @Environment(value=EnvType.CLIENT)
    public void clear() {
        index = 0;
        pool.clear();
    }

    @Environment(value=EnvType.CLIENT)
    public int getSize() {
        return pool.size();
    }

    @Environment(value=EnvType.CLIENT)
    public int getIndex() {
        return index;
    }
}

