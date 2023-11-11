package net.fabricmc.cryptic.gui;

import net.fabricmc.cryptic.Cryptic;
import net.fabricmc.cryptic.events.EventListener;
import net.fabricmc.cryptic.events.types.TickEvent;
import net.minecraft.client.Minecraft;

public class Module {
    protected Minecraft mc = Minecraft.getMinecraft();
    public Module() {
    }

    @EventListener
    public static void onTick(TickEvent.Post e) {

    }
}
