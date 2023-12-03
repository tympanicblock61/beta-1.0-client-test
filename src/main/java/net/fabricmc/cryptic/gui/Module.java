package net.fabricmc.cryptic.gui;

import net.fabricmc.cryptic.utils.KeybindUtils;
import net.fabricmc.cryptic.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Module {
    public Minecraft mc = Minecraft.getMinecraft();
    public final String name;
    public final String description;
    public boolean active = false;
    public KeybindUtils.Key Keybind = KeybindUtils.Key.None;
    public List<Setting<?>> settings = new ArrayList<>();


    public Module(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void render(int x, int y, @NotNull RenderUtils utils) {
        utils.drawWithShadow(this.name, x, y, new Color(0xffffff).getRGB());
    }

    public void onActivate() {
    }

    public void tick() {
    }
}
