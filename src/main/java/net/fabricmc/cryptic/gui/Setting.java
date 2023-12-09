package net.fabricmc.cryptic.gui;

import net.fabricmc.cryptic.utils.KeybindUtils;
import net.fabricmc.cryptic.utils.RenderUtils;
import net.minecraft.client.gui.screen.Screen;

public class Setting<V> extends RenderedObject {

    private V value;
    public final V defaultValue;
    public String name;
    public String description;

    public Setting(String name, String description, V defaultValue) {
        this.name = name;
        this.description = description;
        this.defaultValue = defaultValue;
        reset();
    }

    public void reset() {
        value = defaultValue;
    }

    public void set(V value) {
        this.value = value;
    }

    public V get() {
        return value;
    }

    public void onChange() {
    }
}
