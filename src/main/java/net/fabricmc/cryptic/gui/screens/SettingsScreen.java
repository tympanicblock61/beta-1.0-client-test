package net.fabricmc.cryptic.gui.screens;

import net.fabricmc.cryptic.gui.Module;
import net.fabricmc.cryptic.gui.Setting;
import net.fabricmc.cryptic.utils.KeybindUtils;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class SettingsScreen extends Screen {
    public static SettingsScreen INSTANCE = new SettingsScreen();
    public Module module;

    @Override
    public void render(int mouseX, int mouseY, float tickDelta) {
        if (module != null) {
            int startX = (module.mc.width/2);
            int startY = 50;
            System.out.println(startX);
            System.out.println(startY);
            DrawableHelper.fill(startX, startY, startX+1, startY+(module.mc.height), new Color(0x00ff00).getRGB());
            System.out.println(module);
        }
        // cloud
    }

    @Override
    public void tick() {
        module.settings.forEach(Setting::tick);
    }

    @Override
    protected void keyPressed(char id, int code) {
        if (KeybindUtils.Key.getByCode(code) == KeybindUtils.Key.Escape) {
            this.field_1229.currentScreen = ClickGui.INSTANCE;
            this.field_1229.openScreen(ClickGui.INSTANCE);
        } else {
            module.settings.forEach(setting -> setting.keyPress(KeybindUtils.Key.getByCode(code)));
        }
    }


    @Override
    protected void mouseReleased(int mouseX, int mouseY, int button) {
        module.settings.forEach(setting -> setting.mouseReleased(mouseX, mouseY, button));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        module.settings.forEach(setting -> setting.mouseClicked(mouseX, mouseY, button));
    }

    @Override
    public boolean shouldPauseGame() {
        return false;
    }
}
