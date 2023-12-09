package net.fabricmc.cryptic.gui;


import net.fabricmc.cryptic.utils.RenderUtils;
import net.fabricmc.cryptic.utils.datatypes.Vec2i;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Category extends RenderedObject {
    public String name;
    public String description;
    public boolean closed = false;
    public List<Module> modules = new ArrayList<>();
    public Minecraft mc = Minecraft.getMinecraft();

    public Category(String name, String description, Vec2i pos) {
        this.name = name;
        this.description = description;
        this.pos = pos;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        fill(pos.x, pos.y, pos.x+size.x, closed ? pos.y+mc.textRenderer.fontHeight : pos.y+size.y, new Color(0x634b4b).getRGB());
        fill(pos.x, pos.y, pos.x+size.x, pos.y+mc.textRenderer.fontHeight, new Color(0x000000).getRGB());
        drawWithShadow(name, pos.x, pos.y, new Color(0xffffff).getRGB());
        if (!closed) modules.forEach((module -> {
            if (module.active) fill(pos.x, pos.y+(modules.indexOf(module)*mc.textRenderer.fontHeight)+mc.textRenderer.fontHeight, pos.x+size.x, pos.y+(modules.indexOf(module)*mc.textRenderer.fontHeight)+(mc.textRenderer.fontHeight*2), new Color(0x382B2B).getRGB());
            module.render(pos.x,pos.y+(modules.indexOf(module)*mc.textRenderer.fontHeight)+mc.textRenderer.fontHeight, mouseX, mouseY);
        }));
    }

    public void tick() {
        modules.forEach(RenderedObject::tick);
    }

    @Override
    public void drag(Vec2i to) {
        pos.set(to.x, to.y);
    }

    @Override
    public int getWidth() {
        int longest = 0;
        for (Module module : modules) {
            int newLong = mc.textRenderer.getStringWidth(module.name);
            if (newLong > longest) longest = newLong;
        }
        return longest;
    }

    @Override
    public void init() {
        int totalHeight = (modules.size()*mc.textRenderer.fontHeight)+mc.textRenderer.fontHeight;
        int totalWidth = 0;
        for (Module module : modules) {
            int newLong = mc.textRenderer.getStringWidth(module.name);
            if (newLong > totalWidth) totalWidth = newLong;
        }
        size = Vec2i.create(totalWidth, totalHeight);
    }
}
