package net.fabricmc.cryptic.gui.elements;

import net.fabricmc.cryptic.gui.Element;
import net.fabricmc.cryptic.utils.ClassUtils;
import net.fabricmc.cryptic.utils.datatypes.Vec2i;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HealthBar extends Element {
    public int min;
    public int max;
    public int percent;
    public int one_percent;
    public boolean finished = false;

    public HealthBar(Vec2i pos, Vec2i size) {
        this(pos, size, 0, 10);
    }

    public HealthBar(Vec2i pos, Vec2i size, int min, int max) {
        this.pos = pos;
        this.size = size;
        this.min = min;
        this.max = max;
        this.percent = this.min;
        this.one_percent = this.size.x / this.max;
    }

    @Override
    public void render() {
        fill(pos.x, pos.y, pos.x+size.x, pos.y+size.y, new Color(0xFF0000).getRGB());
        if (percent > size.x) {
            fill(pos.x, pos.y, pos.x+size.x, pos.y+size.y, new Color(0x00FF00).getRGB());
        } else fill(pos.x, pos.y, pos.x+percent, pos.y+size.y, new Color(0x00FF00).getRGB());
    }

    @Override
    public void drag(@NotNull Vec2i to) {
        pos.set(to.x, to.y);
    }

    public void add() {
        if (percent < size.x) percent += one_percent;
    }

    @Override
    public void tick() {
        if (mc.playerEntity != null && !finished) {
            List<Class<?>> types = new ArrayList<>(2);
            types.add(int.class);
            types.add(float.class);
            ClassUtils.dumpClassFields(mc.playerEntity, types);
            finished = true;
        }
    }
}