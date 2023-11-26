package net.fabricmc.cryptic.gui.elements;

import net.fabricmc.cryptic.gui.Element;
import net.fabricmc.cryptic.utils.RenderUtils;
import net.fabricmc.cryptic.utils.datatypes.Vec2i;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class TextElement extends Element {
    String message;
    float dragPercent = 0f;
    Vec2i dragTo;
    boolean wasDragged;
    boolean wasHovered;

    public TextElement(String message) {
        this.message = message;
        pos = Vec2i.create(300, 100);
        wasHovered = false;
    }


    @Override
    public void init(RenderUtils utils) {
        int width = mc.textRenderer.getStringWidth(message);
        int height = mc.textRenderer.getHeightSplit(message, mc.textRenderer.getStringWidth(message))/2;
        size = Vec2i.create(width, height);
    }

    @Override
    public void drag(@NotNull Vec2i to) {
        System.out.println("dragged: "+message);
        wasDragged = true;
        dragTo = to;
    }

    @Override
    public void render(@NotNull RenderUtils utils) {
        if (wasDragged) {
            if (dragPercent >= 1.0f) {
                dragPercent = 0f;
                wasDragged = false;
            } else {
                dragPercent += 0.1f;
                pos = lerp(pos, dragTo, dragPercent);
            }
        } else if (wasHovered) {
            RenderUtils.fill(pos.x, pos.y, pos.x+size.x, pos.y+size.y, new Color(0x6E000000, true).getRGB());
        }
        utils.drawWithShadow(message, pos.x, pos.y, new Color(0x0000ff).getRGB());
        wasHovered = false;
    }

    @Override
    public void hover(RenderUtils utils) {
        this.wasHovered = true;
    }

    @Contract(pure = true)
    private static @NotNull Vec2i lerp(@NotNull Vec2i start, @NotNull Vec2i end, float percentage) {
        int x = (int) (start.x + percentage * (end.x - start.x));
        int y = (int) (start.y + percentage * (end.y - start.y));
        return Vec2i.create(x, y);
    }
}
