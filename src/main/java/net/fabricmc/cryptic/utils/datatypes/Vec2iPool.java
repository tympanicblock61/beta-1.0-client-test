package net.fabricmc.cryptic.utils.datatypes;

import java.util.ArrayList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.Vec3dPool;

public class Vec2iPool {
    private final int cleanInterval;
    private final int cleanAmount;
    private final List<Vec2i> pool = new ArrayList<>();
    private int index = 0;
    private int highestIndex = 0;
    private int tick = 0;

    public Vec2iPool(int i2, int j2) {
        cleanInterval = i2;
        cleanAmount = j2;
    }

    public Vec2i getOrCreate(int x, int y) {
        Vec2i Vec2i;
        if (index >= pool.size()) {
            Vec2i = new Vec2i(x, y);
            pool.add(Vec2i);
        } else {
            Vec2i = pool.get(index);
            Vec2i.set(x, y);
        }
        ++index;
        return Vec2i;
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

