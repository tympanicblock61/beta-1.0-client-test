package net.fabricmc.cryptic.gui.components;

import net.fabricmc.cryptic.gui.RenderedObject;
import net.fabricmc.cryptic.utils.RenderUtils;
import net.fabricmc.cryptic.utils.datatypes.Vec2i;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ScrollableList extends RenderedObject {

    int scrollBarWidth = 10;
    int scrollAmount = 10;
    int yOffset = 0;
    Color background = Color.BLACK;
    Color scrollbar = Color.GRAY;
    List<String> strings = new ArrayList<>();
    Consumer<String> onclick = null;

    public ScrollableList() {}

    public ScrollableList(int scrollBarWidth, Color background, Color scrollbar, List<String> strings, Consumer<String> onclick) {
        this.scrollBarWidth = scrollBarWidth;
        this.background = background;
        this.scrollbar = scrollbar;
        this.strings = strings;
        this.onclick = onclick;
    }

    public ScrollableList(int x, int y, int width, int height, int scrollBarWidth, Color background, Color scrollbar, List<String> strings, Consumer<String> click) {
        this(scrollBarWidth, background, scrollbar, strings, click);
        pos = Vec2i.create(x, y);
        size = Vec2i.create(width, height);
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

    public void render(int x, int y, int width, int height) {
        pos = Vec2i.create(x,y);
        size = Vec2i.create(width, height);
        render();
    }

    @Override
    public void render() {
        HandleMouseScroll();
        RenderUtils.fill(pos.x - size.x, pos.y, pos.x + size.x + scrollBarWidth, pos.y + size.y, background.getRGB());

        // Calculate visible elements based on yOffset and visibleHeight
        List<String> sortedStrings = strings
                .stream()
                .skip(yOffset / mc.textRenderer.fontHeight) // Skip elements above the visible area
                .limit(size.y / mc.textRenderer.fontHeight) // Limit displayed elements by visible area
                .collect(Collectors.toList());

        // Render visible elements
        for (int i = 0; i < sortedStrings.size(); i++) {
            String string = sortedStrings.get(i);
            drawWithShadow(mc.textRenderer.trimToWidth(string, size.x), pos.x - size.x, pos.y + (mc.textRenderer.fontHeight * i) + yOffset % mc.textRenderer.fontHeight, new Color(0xffffff).getRGB());
        }

        // Calculate scrollbar position and height relative to the visible area
        float contentHeight = strings.size() * mc.textRenderer.fontHeight;
        float scrollBarHeight = (size.y / contentHeight) * size.y;
        float scrollBarY = (yOffset / contentHeight) * (size.y- scrollBarHeight);

        // Render scroll bar
        RenderUtils.fill(pos.x + size.x - scrollBarWidth, (int) (pos.y + scrollBarY), pos.x + size.x, Math.min((int) (pos.y + scrollBarHeight + scrollBarY), pos.y + size.y), scrollbar.getRGB());
    }

    public void HandleMouseScroll() {
        int dWheel = Mouse.getDWheel();

        if (dWheel < 0 && yOffset > 0) {
            yOffset -= scrollAmount; // Scroll up
        } else if (dWheel > 0 && yOffset < strings.size() * mc.textRenderer.fontHeight) {
            yOffset += scrollAmount; // Scroll down
        }
    }

    private int getXOffset() {
        int offset = 0;
        for (String string : strings) {
            if (mc.textRenderer.getStringWidth(string) > offset) offset = mc.textRenderer.getStringWidth(string);
        }
        return offset;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {

        List<String> sortedStrings = strings
                .stream()
                .skip(yOffset / mc.textRenderer.fontHeight) // Skip elements above the visible area
                .limit(size.y / mc.textRenderer.fontHeight) // Limit displayed elements by visible area
                .collect(Collectors.toList());

        for (int i = 0; i < sortedStrings.size(); i++) {
            String string = sortedStrings.get(i);
            Vec2i elementPos = Vec2i.create(pos.x - size.x, pos.y + (mc.textRenderer.fontHeight * i) + yOffset % mc.textRenderer.fontHeight);
            Vec2i elementSize = Vec2i.create(mc.textRenderer.getStringWidth(string), mc.textRenderer.fontHeight); // Replace width calculation

            // Check if the click is inside the element's bounds
            Vec2i clickPos = Vec2i.create(mouseX, mouseY);
            if (inBox(clickPos, elementPos, elementSize)) {
                if (onclick != null) onclick.accept(string);
                break;
            }
        }
    }
}
