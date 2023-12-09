package net.fabricmc.cryptic.gui;

import net.fabricmc.cryptic.gui.screens.HudEditor;
import net.fabricmc.cryptic.utils.RenderUtils;
import net.fabricmc.cryptic.utils.datatypes.Vec2i;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Element extends RenderedObject {

    public Element() {
    }

    public Element(Vec2i pos, Vec2i size) {
    }

    public boolean inBox(Vec2i mousePos) {
        Vec2i RealSize;
        if (HudEditor.INSTANCE.drawBackground) {
            RealSize = Vec2i.create(getSize().x + 2 * HudEditor.INSTANCE.borderSize, getSize().y + 2 * HudEditor.INSTANCE.borderSize);
        } else RealSize = getSize();
        return mousePos.x >= pos.x && mousePos.x <= pos.x + RealSize.x && mousePos.y >= pos.y && mousePos.y <= pos.y + RealSize.y;
    }

    public void drawBackground(@NotNull Vec2i colors) {
        fill(getPos().x-HudEditor.INSTANCE.borderSize, pos.y-HudEditor.INSTANCE.borderSize, getPos().x+getSize().x+HudEditor.INSTANCE.borderSize, getPos().y+getSize().y+HudEditor.INSTANCE.borderSize, new Color(colors.x).getRGB());
        fill(getPos().x, getPos().y, getPos().x+getSize().x, getPos().y+getSize().y, new Color(colors.y).getRGB());
    }
}
