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
        if (clickedElement != null && !clickPos.equals(Vec2i.create(mouseX, mouseY))) {
            int offsetX = mouseX - dragOffset.x;
            int offsetY = mouseY - dragOffset.y;
            clickedElement.drag(Vec2i.create(offsetX, offsetY));
        }
        if (lastMousePos.equals(Vec2i.create(mouseX,mouseY))) {
            for (Element element : elements) {
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
            for (Element element : elements) {
                Vec2i size;
                if (drawBackground) {
                    size = Vec2i.create(element.getSize().x + 2 * borderSize, element.getSize().y + 2 * borderSize);
                } else size = Vec2i.create(element.getSize().x, element.getSize().y);
                if (render.inBox(Vec2i.create(mouseX, mouseY), element.getPos(), size)) {
                    clickPos = Vec2i.create(mouseX, mouseY);
                    clickedElement = element;
                    dragOffset = Vec2i.create(mouseX-element.getPos().x, mouseY-element.getPos().y);
                    break;
                }
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
