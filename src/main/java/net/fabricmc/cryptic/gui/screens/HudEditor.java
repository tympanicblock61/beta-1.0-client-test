package net.fabricmc.cryptic.gui.screens;

import net.fabricmc.cryptic.Cryptic;
import net.fabricmc.cryptic.events.types.KeyEventListener;
import net.fabricmc.cryptic.gui.Element;
import net.fabricmc.cryptic.utils.KeybindUtils;
import net.fabricmc.cryptic.utils.RenderUtils;
import net.fabricmc.cryptic.utils.datatypes.Vec2i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;

import java.util.LinkedHashSet;

public class HudEditor extends Screen implements KeyEventListener {
    public static HudEditor INSTANCE = new HudEditor();
    public static Minecraft mc = Minecraft.getMinecraft();
    public LinkedHashSet<Element> elements = new LinkedHashSet<>();
    public RenderUtils render = new RenderUtils();
    private Element clickedElement = null;
    private Vec2i clickPos = null;
    private Vec2i dragOffset = null;
    public boolean drawBackground = true;
    public int borderSize = 10;
    public KeybindUtils.Key keybind = KeybindUtils.Key.Period;
    public long opened;
    public Vec2i lastMousePos = Vec2i.create(0,0);

    public HudEditor() {
        Cryptic.EventBus.subscribe(Event.ID, this);
    }

    @Override
    public void render(int mouseX, int mouseY, float tickDelta) {
        Vec2i mousePos = Vec2i.create(mouseX, mouseY);
        if (clickedElement != null && !clickPos.equals(Vec2i.create(mouseX, mouseY))) {
            int offsetX = mouseX - dragOffset.x;
            int offsetY = mouseY - dragOffset.y;
            clickedElement.drag(Vec2i.create(offsetX, offsetY));
        }
        if (lastMousePos.equals(mousePos)) {
            for (Element element : elements) {
                if (element.inBox(mousePos)) {
                    element.hover();
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
        KeybindUtils.Key key = KeybindUtils.Key.getByCode(code);
        if ((key == keybind || key == KeybindUtils.Key.Escape) && System.currentTimeMillis() - opened >= 200) {
            mc.openScreen(null);
            mc.closeScreen();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        Vec2i mousePos = Vec2i.create(mouseX, mouseY);
        if (button == 0) {
            for (Element element : elements) {
                if (element.inBox(mousePos)) {
                    clickPos = mousePos;
                    clickedElement = element;
                    dragOffset = Vec2i.create(mouseX-element.getPos().x, mouseY-element.getPos().y);
                    break;
                }
            }
        } else if (button == 1) {
            boolean inElement = false;
            for (Element element : elements) {
                if (element.inBox(mousePos)) {
                    inElement = true;
                    break;
                }
            }
            if (!inElement) {
                ElementPicker.INSTANCE.clickPos = mousePos;
                field_1229.currentScreen = ElementPicker.INSTANCE;
                field_1229.openScreen(ElementPicker.INSTANCE);
            }
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int button) {
        Vec2i newPos = Vec2i.create(mouseX, mouseY);
        if (newPos.equals(clickPos) || (clickedElement != null && button == 0)) clickedElement = null;
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
