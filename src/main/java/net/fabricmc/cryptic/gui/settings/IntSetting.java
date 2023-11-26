package net.fabricmc.cryptic.gui.settings;

import net.fabricmc.cryptic.gui.Setting;
import net.fabricmc.cryptic.utils.RenderUtils;

import java.awt.*;

public class IntSetting extends Setting<Integer> {

    public int min;
    public int max;

    public IntSetting(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public void render(int x, int y ,RenderUtils utils) {
        RenderUtils.fill(x, y, x+10, y+10, new Color(0x180080).getRGB());
    }
}
