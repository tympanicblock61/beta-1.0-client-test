package net.fabricmc.cryptic.gui;

import net.fabricmc.cryptic.events.types.KeyEventListener;
import net.fabricmc.cryptic.utils.KeybindUtils;
import net.fabricmc.cryptic.utils.RenderUtils;
import net.fabricmc.cryptic.utils.datatypes.Vec2i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;

import java.util.LinkedHashSet;

public class HudEditor extends Screen implements KeyEventListener {
    public static HudEditor INSTANCE = new HudEditor();
    public static Minecraft mc = Minecraft.getMinecraft();
    public LinkedHashSet<HudElement> hudElements = new LinkedHashSet<>();
    public RenderUtils render = new RenderUtils();
    public HudElement clickedElement;
    public boolean drawBackground = true;
    public int borderSize = 5;
    public Vec2i clickPos;
    public KeybindUtils.Key keybind = KeybindUtils.Key.Period;
    public long opened;
    public Vec2i lastMousePos = Vec2i.create(0,0);

    @Override
    public void render(int mouseX, int mouseY, float tickDelta) {
        if (lastMousePos.equals(Vec2i.create(mouseX,mouseY))) {
            for (HudElement element : hudElements) {
                Vec2i size;
                if (drawBackground) {
                    size = Vec2i.create(element.getSize().x + 2 * borderSize, element.getSize().y + 2 * borderSize);
                } else size = Vec2i.create(element.getSize().x, element.getSize().y);
                if (render.inBox(Vec2i.create(mouseX, mouseY), element.getPos(), size)) {
                    element.hover(render);
                    System.out.println(element);
                    break;
                }
            }
        }
        lastMousePos.set(mouseX, mouseY);
    }

    @Override
    protected void keyPressed(char id, int code) {
        System.out.println("screen key press");
        System.out.println(KeybindUtils.Key.getByCode(code).name);
        System.out.println(code);
        if ((code == keybind.keyCode || code == KeybindUtils.Key.Escape.keyCode) && System.currentTimeMillis() - opened >= 200) {
            mc.openScreen(null);
            mc.closeScreen();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        if (button == 0) {
            for (HudElement element : hudElements) {
                Vec2i size;
                if (drawBackground) {
                    size = Vec2i.create(element.getSize().x + 2 * borderSize, element.getSize().y + 2 * borderSize);
                } else size = Vec2i.create(element.getSize().x, element.getSize().y);
                if (render.inBox(Vec2i.create(mouseX, mouseY), element.getPos(), size)) {
                    clickPos = Vec2i.create(mouseX, mouseY);
                    clickedElement = element;
                    break;
                }
            }
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int button) {
        Vec2i newPos = Vec2i.create(mouseX, mouseY);
        if (newPos.equals(clickPos)) {
            clickedElement = null;
            return;
        }
        if (clickedElement != null && button == 0) {
            clickedElement.drag(Vec2i.create(mouseX, mouseY));
            clickedElement = null;
        }
    }

    @Override
    public boolean shouldPauseGame() {
        return false;
    }

    @Override
    public void onPress(char eventChar, int eventKey) {
        if (eventKey == INSTANCE.keybind.keyCode && mc.currentScreen != INSTANCE && KeybindUtils.pressed.contains(INSTANCE.keybind)) {
            INSTANCE.opened = System.currentTimeMillis();
            mc.openScreen(INSTANCE);
        }
    }

    @Override
    public void onRelease(char eventChar, int eventKey) {}
}
