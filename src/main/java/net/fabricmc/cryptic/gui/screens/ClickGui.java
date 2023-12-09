package net.fabricmc.cryptic.gui.screens;

import net.fabricmc.cryptic.Cryptic;
import net.fabricmc.cryptic.events.types.KeyEventListener;
import net.fabricmc.cryptic.events.types.MouseEventListener;
import net.fabricmc.cryptic.gui.Category;
import net.fabricmc.cryptic.gui.Module;
import net.fabricmc.cryptic.utils.KeybindUtils;
import net.fabricmc.cryptic.utils.RenderUtils;
import net.fabricmc.cryptic.utils.datatypes.Vec2i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import org.lwjgl.opengl.GL11;

import java.util.LinkedHashSet;

import static net.fabricmc.cryptic.utils.KeybindUtils.Key;

public class ClickGui extends Screen implements KeyEventListener, MouseEventListener {
    public static ClickGui INSTANCE = new ClickGui();
    public Key keybind = Key.RightShift;
    public long opened;
    public Minecraft mc = Minecraft.getMinecraft();
    public LinkedHashSet<Category> categories = new LinkedHashSet<>();
    public RenderUtils utils = new RenderUtils();
    private static Vec2i clickPos = null;
    private static Category clickedCategory = null;
    private static Vec2i dragOffset = null;
    public float scaleX = 1F;
    public float scaleY = 1F;

    public ClickGui() {
        Cryptic.EventBus.subscribe(KeyEventListener.Event.ID, this);
        Cryptic.EventBus.subscribe(MouseEventListener.Event.ID, this);
    }

    public Category getCategory(String name) {
        return categories.stream().findAny().filter(category -> category.name.equals(name)).orElse(null);
    }

    @Override
    public void render(int mouseX, int mouseY, float tickDelta) {
        GL11.glScalef(ClickGui.INSTANCE.scaleX, ClickGui.INSTANCE.scaleY, 1.0f);
        if (clickedCategory != null && !clickPos.equals(Vec2i.create(mouseX, mouseY))) {
            int offsetX = mouseX - dragOffset.x;
            int offsetY = mouseY - dragOffset.y;
            clickedCategory.drag(Vec2i.create(offsetX, offsetY));
        }
        for (Category category : categories) {
            category.init();
            category.render(mouseX, mouseY);
        }
    }

    @Override
    public void tick() {
        categories.forEach(Category::tick);
    }

    @Override
    protected void keyPressed(char id, int code) {
        System.out.println("screen key press");
        System.out.println(Key.getByCode(code).name);
        System.out.println(code);
        if ((code == keybind.keyCode || code == Key.Escape.keyCode) && System.currentTimeMillis() - opened >= 200) {
            mc.openScreen(null);
            mc.closeScreen();
            GL11.glScalef(1.0f, 1.0f, 1.0f);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        Vec2i mouse = Vec2i.create(mouseX, mouseY);
        if (button == 0) {
            for (Category category : categories) {
                if (utils.inBox(mouse, category.getPos(), Vec2i.create(category.getWidth(), mc.textRenderer.fontHeight))) {
                    clickPos = Vec2i.create(mouseX, mouseY);
                    clickedCategory = category;
                    dragOffset = Vec2i.create(mouseX - category.getPos().x, mouseY - category.getPos().y);
                    break;
                }
            }
            for (Category category : categories) {
                for (Module module : category.modules) {
                    int x = category.getPos().x;
                    int y = category.getPos().y + (category.modules.indexOf(module) * mc.textRenderer.fontHeight)+mc.textRenderer.fontHeight;
                    RenderUtils.fill(x,y, x+category.getWidth(), mc.textRenderer.fontHeight, 0x00);
                    if (utils.inBox(mouse, Vec2i.create(x, y), Vec2i.create(category.getWidth(), mc.textRenderer.fontHeight))) {
                        module.active = !module.active;
                        module.onActivate();
                        break;
                    }
                }
            }
        } else if (button == 1) {
            for (Category category : categories) {
                if (utils.inBox(mouse, category.getPos(), Vec2i.create(category.getWidth(), mc.textRenderer.fontHeight))) {
                    category.closed = !category.closed;
                    break;
                }
            }
            for (Category category : categories) {
                for (Module module : category.modules) {
                    int x = category.getPos().x;
                    int y = category.getPos().y + (category.modules.indexOf(module) * mc.textRenderer.fontHeight)+mc.textRenderer.fontHeight;
                    RenderUtils.fill(x,y, x+category.getWidth(), mc.textRenderer.fontHeight, 0x00);
                    if (utils.inBox(mouse, Vec2i.create(x, y), Vec2i.create(category.getWidth(), mc.textRenderer.fontHeight))) {
                        SettingsScreen.INSTANCE.module = module;
                        mc.openScreen(SettingsScreen.INSTANCE);
                        break;
                    }
                }
            }
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int button) {
        //TODO add drag for categories
        Vec2i newPos = Vec2i.create(mouseX, mouseY);
        if (newPos.equals(clickPos) || (clickedCategory != null && button == 0)) clickedCategory = null;
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
