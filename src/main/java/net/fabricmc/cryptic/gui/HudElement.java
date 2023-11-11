package net.fabricmc.cryptic.gui;

import net.fabricmc.cryptic.Cryptic;
import net.fabricmc.cryptic.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import org.lwjgl.input.Keyboard;

import java.nio.charset.MalformedInputException;

public class HudElement {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Minecraft mc = Minecraft.getMinecraft();

    public HudElement() {
        x = 100;
        y = 100;
        width = 10;
        height = 10;
    }

    public void render(RenderUtils utils) {
    }

    public void init() {
    }

    public void tick() {
    }
}
