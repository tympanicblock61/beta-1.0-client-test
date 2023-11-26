package net.fabricmc.cryptic.gui;

import net.fabricmc.cryptic.utils.RenderUtils;

public class Setting<V> {

    private V value;

    public Setting() {
    }

    public void set(V value) {
        this.value = value;
    }

    public V get() {
        return value;
    }

    public void render(int x, int y, RenderUtils utils) {
    }

    public void tick() {
    }
}
