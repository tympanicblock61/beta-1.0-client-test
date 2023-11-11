package net.fabricmc.cryptic.gui.hudelements;

import net.fabricmc.cryptic.gui.HudElement;
import net.fabricmc.cryptic.utils.RenderUtils;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class TextElement extends HudElement {

    String message = "";
    public TextElement(String message) {
        this.message = message;
        x = 300;
        y = 100;
    }


    @Override
    public void init() {
        width = mc.textRenderer.getStringWidth(message);
        System.out.println("init");
        height = mc.textRenderer.getHeightSplit(message, mc.textRenderer.getStringWidth(message))/2;
    }

    @Override
    public void render(@NotNull RenderUtils utils) {
        System.out.println("sizes");
        System.out.println(width);
        System.out.println(height);
        utils.drawWithShadow("ssss", x-width, y, new Color(0x0000ff).getRGB());
    }
}
