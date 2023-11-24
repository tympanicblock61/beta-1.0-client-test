package net.fabricmc.cryptic.gui;

import net.fabricmc.cryptic.utils.RenderUtils;
import net.fabricmc.cryptic.utils.datatypes.Vec2i;
import net.minecraft.client.Minecraft;

public class HudElement {
    public Vec2i pos;
    public Vec2i size;
    protected Minecraft mc = Minecraft.getMinecraft();

    public HudElement() {
    }

    public Vec2i getSize() {
        return size;
    }

    public Vec2i getPos() {
        return pos;
    }

    public void render(RenderUtils utils) {
    }

    public void init(RenderUtils utils) {
    }

    public void drag(Vec2i to) {
    }

    public void hover(RenderUtils utils) {
    }

    public void tick(RenderUtils utils) {
    }
}
