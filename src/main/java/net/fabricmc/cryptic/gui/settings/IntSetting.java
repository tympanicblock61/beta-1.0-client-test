package net.fabricmc.cryptic.gui.settings;

import net.fabricmc.cryptic.gui.Setting;
import net.fabricmc.cryptic.utils.RenderUtils;

import java.awt.*;

public class IntSetting extends Setting<Integer> {

    public int min;
    public int max;

    public IntSetting(String name, String description, Integer defaultValue, int min, int max) {
        super(name, description, defaultValue);
        this.min = min;
        this.max = max;
    }

    @Override
    public void render(int x, int y, int mouseX, int mouseY) {
        fill(x, y, x+10, y+10, new Color(0x180080).getRGB());
    }
}
