package net.fabricmc.cryptic.gui;

import net.fabricmc.cryptic.events.types.KeyEventListener;
import net.fabricmc.cryptic.events.types.MouseEventListener;
import net.fabricmc.cryptic.utils.KeybindUtils;
import net.fabricmc.cryptic.utils.datatypes.Vec2i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;

import static net.fabricmc.cryptic.utils.KeybindUtils.Key;

public class ClickGui extends Screen implements KeyEventListener, MouseEventListener {
    public static ClickGui INSTANCE = new ClickGui();
    public Key keybind = Key.RightShift;
    public long opened;
    public static Minecraft mc = Minecraft.getMinecraft();
    @Override
    public void render(int mouseX, int mouseY, float tickDelta) {
        fill(0, 0, 100, 100, 0xffffffff);
        drawWithShadow(textRenderer, "hello", 100 - textRenderer.getStringWidth("hello"), 100-(textRenderer.getHeightSplit("hello", textRenderer.getStringWidth("hello"))/2), 0xff00ff);
    }

    @Override
    protected void keyPressed(char id, int code) {
        System.out.println("screen key press");
        System.out.println(Key.getByCode(code).name);
        System.out.println(code);
        if ((code == keybind.keyCode || code == Key.Escape.keyCode) && System.currentTimeMillis() - opened >= 200) {
            mc.openScreen(null);
            mc.closeScreen();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        //TODO add drag for categories

        /*if (button == 0) {
            for (HudElement element : hudElements) {
                if (render.inBox(Vec2i.create(mouseX, mouseY), element.pos, element.size)) {
                    clickPos = Vec2i.create(mouseX, mouseY);
                    clickedElement = element;
                    break;
                }
            }
        }*/
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int button) {
        //TODO add drag for categories

        /*Vec2i newPos = Vec2i.create(mouseX, mouseY);

        if (newPos.equals(clickPos)) {
            clickedElement = null;
            return;
        }
        if (clickedElement != null && button == 0) {
            clickedElement.drag(Vec2i.create(mouseX, mouseY));
            clickedElement = null;
        }*/
    }

    @Override
    public boolean shouldPauseGame() {
        return false;
    }

    @Override
    public void onPress(char eventChar, int eventKey) {
        System.out.println("key press");
        System.out.println(Key.getByCode(eventKey));
        System.out.println(eventKey);
        if (eventKey == INSTANCE.keybind.keyCode && mc.currentScreen != INSTANCE && KeybindUtils.pressed.contains(INSTANCE.keybind)) {
            INSTANCE.opened = System.currentTimeMillis();
            mc.openScreen(INSTANCE);
        }
    }

    @Override
    public void onRelease(char eventChar, int eventKey) {}

    @Override
    public void onClick(Vec2i pos, int button) {

    }

    @Override
    public void onRelease(Vec2i pos, int button) {

    }

    @Override
    public void onDrag(Vec2i from, Vec2i to, int button) {
        System.out.println("drag");
        System.out.println(from);
        System.out.println(to);
        System.out.println(button);
    }
}
