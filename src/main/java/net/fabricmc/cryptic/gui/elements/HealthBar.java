package net.fabricmc.cryptic.gui.elements;

import net.fabricmc.cryptic.gui.Element;
import net.fabricmc.cryptic.utils.ClassUtils;
import net.fabricmc.cryptic.utils.RenderUtils;
import net.fabricmc.cryptic.utils.datatypes.Vec2i;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HealthBar extends Element {
    public Vec2i pos;
    public Vec2i size;
    public int min;
    public int max;
    public int percent;
    public int one_percent;
    public boolean finished = false;

    public HealthBar(Vec2i pos, Vec2i size, int min, int max) {
        this.pos = pos;
        this.size = size;
        this.min = min;
        this.max = max;
        this.percent = this.min;
        this.one_percent = this.size.x / this.max;
    }

    @Override
    public void render(RenderUtils utils) {
        //context.fillStyle = "black"
        //context.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight())
        //context.fillStyle = "red"
        RenderUtils.fill(pos.x, pos.y, pos.x+size.x, pos.y+size.y, new Color(0xFF0000).getRGB());
        //context.fillRect(this.x, this.y, this.length, this.height)
        //context.fillStyle = "green"
        //if (this.percent > this.length) {
        //    context.fillRect(this.x, this.y, this.length, this.height)
        //} else context.fillRect(this.x, this.y, this.percent, this.height)

        if (percent > size.x) {
            RenderUtils.fill(pos.x, pos.y, pos.x+size.x, pos.y+size.y, new Color(0x00FF00).getRGB());
        } else RenderUtils.fill(pos.x, pos.y, pos.x+percent, pos.y+size.y, new Color(0x00FF00).getRGB());
    }

    @Override
    public Vec2i getSize() {
        return size;
    }

    @Override
    public Vec2i getPos() {
        return pos;
    }

    @Override
    public void drag(@NotNull Vec2i to) {
        pos.set(to.x, to.y);
    }

    public void add() {
        if (percent < size.x) percent += one_percent;
    }

    @Override
    public void tick(RenderUtils utils) {
        if (mc.playerEntity != null && !finished) {
            List<Class<?>> types = new ArrayList<>(2);
            types.add(int.class);
            types.add(float.class);
            ClassUtils.dumpClassFields(mc.playerEntity, types);
            finished = true;
        }
    }
}