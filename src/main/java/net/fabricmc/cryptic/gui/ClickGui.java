package net.fabricmc.cryptic.gui;

import net.fabricmc.cryptic.Cryptic;
import net.fabricmc.cryptic.events.EventListener;
import net.fabricmc.cryptic.events.types.KeyEvent;
import net.fabricmc.cryptic.utils.KeybindUtils;
import net.fabricmc.cryptic.utils.RenderUtils;
import net.fabricmc.cryptic.utils.datatypes.Vec2i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class ClickGui extends Screen {

    public static Minecraft mc = Minecraft.getMinecraft();
    public static ClickGui INSTANCE = new ClickGui();
    public int keybind = Keyboard.KEY_RSHIFT;
    public long opened;

    public List<HudElement> hudElements = new ArrayList<>();
    public RenderUtils render = new RenderUtils();

    @Override
    public void render(int mouseX, int mouseY, float tickDelta) {
        fill(0, 0, 100, 100, 0xffffffff);
        drawWithShadow(textRenderer, "hello", 100 - textRenderer.getStringWidth("hello"), 100-(textRenderer.getHeightSplit("hello", textRenderer.getStringWidth("hello"))/2), 0xff00ff);
        for (HudElement hudElement : hudElements) {
            hudElement.init();
            render.drawBackground(Vec2i.create(hudElement.x, hudElement.y), Vec2i.create(hudElement.width, hudElement.height), Vec2i.create(0x000000, 0x634b4b), 5);
            hudElement.render(render);
        }
    }

    @Override
    protected void keyPressed(char id, int code) {
        System.out.println("screen key press");
        System.out.println(KeybindUtils.getKeyName(code));
        System.out.println(KeybindUtils.getKeyName(id));
        if ((code == keybind || code == Keyboard.KEY_ESCAPE) && System.currentTimeMillis() - opened >= 500) {
            mc.openScreen(null);
            mc.closeScreen();
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int button) {

    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int button) {

    }



    @Override
    public boolean shouldPauseGame() {
        return false;
    }

    @EventListener
    public static void onKeyPress(KeyEvent.@NotNull Press event) {
        System.out.println("key press event ");
        System.out.println(event.eventChar);
        System.out.println(event.eventKey);
        System.out.println(KeybindUtils.getKeyName(event.eventKey));
        if (event.eventKey == INSTANCE.keybind && mc.currentScreen != INSTANCE && KeybindUtils.pressed.contains(INSTANCE.keybind)) {
            INSTANCE.opened = System.currentTimeMillis();
            mc.openScreen(INSTANCE);
        }
    }

}
