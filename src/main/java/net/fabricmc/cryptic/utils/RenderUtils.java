package net.fabricmc.cryptic.utils;

import net.fabricmc.cryptic.Cryptic;
import net.fabricmc.cryptic.utils.datatypes.Vec2i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DrawableHelper;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class RenderUtils extends DrawableHelper {
    public Minecraft mc = Minecraft.getMinecraft();

    public void drawTexture(String path ,int x, int y, int u, int v, int width, int height) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glBindTexture(3553, mc.textureManager.getTextureFromPath(path));
        this.drawTexture(x, y, u, v, width, height);
    }

    public void drawWithShadow(String text, int x, int y, int color) {
        drawWithShadow(mc.textRenderer, text, x, y, color);
    }

    // pos
    // x
    // y

    // size
    // width
    // height

    // colors
    // border
    // fill
    public void drawBackground(@NotNull Vec2i pos, @NotNull Vec2i size, @NotNull Vec2i colors, int border) {
        fill(pos.x-border, pos.y-border, pos.x+size.x+border, pos.y+size.y+border, new Color(colors.x).getRGB());
        fill(pos.x, pos.y, pos.x+size.x, pos.y+size.y, new Color(colors.y).getRGB());
    }


    public boolean inBox(@NotNull Vec2i clickPos, @NotNull Vec2i pos, @NotNull Vec2i size) {
        return clickPos.x >= pos.x && clickPos.x <= pos.x + size.x && clickPos.y >= pos.y && clickPos.y <= pos.y + size.y;
    }
}
