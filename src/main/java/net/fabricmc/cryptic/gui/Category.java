package net.fabricmc.cryptic.gui;


import net.fabricmc.cryptic.utils.RenderUtils;
import net.fabricmc.cryptic.utils.datatypes.Vec2i;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Category {

    public Vec2i location;
    public String name;
    public String description;
    public boolean closed = false;
    public List<Module> modules = new ArrayList<>();
    public Minecraft mc = Minecraft.getMinecraft();

    public Category(String name, String description, Vec2i location) {
        this.name = name;
        this.description = description;
        this.location = location;
    }

    public void render(RenderUtils utils) {
        int totalHeight = (modules.size()*mc.textRenderer.fontHeight)+mc.textRenderer.fontHeight;
        int length = getLength();
        RenderUtils.fill(location.x, location.y, location.x+length, closed ? location.y+mc.textRenderer.fontHeight : location.y+totalHeight, new Color(0x634b4b).getRGB());
        RenderUtils.fill(location.x, location.y, location.x+length, location.y+mc.textRenderer.fontHeight, new Color(0x000000).getRGB());
        utils.drawWithShadow(this.name, location.x, location.y, new Color(0xffffff).getRGB());
        if (!closed) modules.forEach((module -> {
            if (module.active) RenderUtils.fill(location.x, location.y+(modules.indexOf(module)*mc.textRenderer.fontHeight)+mc.textRenderer.fontHeight, location.x+length, location.y+(modules.indexOf(module)*mc.textRenderer.fontHeight)+(mc.textRenderer.fontHeight*2), new Color(0x382B2B).getRGB());
            module.render(location.x,location.y+(modules.indexOf(module)*mc.textRenderer.fontHeight)+mc.textRenderer.fontHeight, utils);
        }));

    }

    public void tick() {
        modules.forEach(Module::tick);
    }

    public void drag(Vec2i to) {
        this.location.set(to.x, to.y);
    }

    public int getLength() {
        int longest = 0;
        for (Module module : modules) {
            int newLong = mc.textRenderer.getStringWidth(module.name);
            if (newLong > longest) longest = newLong;
        }
        return longest;
    }
}
