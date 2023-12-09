package net.fabricmc.cryptic.gui;

import net.fabricmc.cryptic.utils.KeybindUtils;
import net.fabricmc.cryptic.utils.RenderUtils;
import net.fabricmc.cryptic.utils.datatypes.Vec2i;
import net.minecraft.client.Minecraft;

public class RenderedObject extends RenderUtils {
    protected Minecraft mc = Minecraft.getMinecraft();
    protected Vec2i pos = null;
    protected Vec2i size = null;

    public void keyPress(KeybindUtils.Key key) {
    }

    public void keyRelease(KeybindUtils.Key key) {
    }

    public void mouseReleased(int mouseX, int mouseY, int button) {
    }

    public void mouseClicked(int mouseX, int mouseY, int button) {
    }

    public void render() {
    }

    public void render(int mouseX, int mouseY) {
    }

    public void render(int x, int y, int mouseX, int mouseY) {
    }

    public void drag(Vec2i to) {
    }

    public void hover() {
    }

    public void tick() {
    }

    public void init() {
    }

    public int getX() {
        return pos.x;
    }

    public int getY() {
        return pos.y;
    }

    public void setPos(Vec2i pos) {
        this.pos = pos;
    }

    public Vec2i getPos() {
        return pos;
    }

    public int getWidth() {
        return size.x;
    }

    public int getHeight() {
        return size.y;
    }

    public Vec2i getSize() {
        return size;
    }

    public void setSize(Vec2i size) {
        this.size = size;
    }
}
