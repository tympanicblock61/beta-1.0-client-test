package net.fabricmc.cryptic;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.cryptic.events.EventBus;
import net.fabricmc.cryptic.gui.ClickGui;
import net.fabricmc.cryptic.gui.hudelements.TextElement;
import net.minecraft.client.Minecraft;

public class Cryptic implements ModInitializer, ClientModInitializer {
	public static Minecraft mc = Minecraft.getMinecraft();
	public static EventBus EventBus = new EventBus();


	@Override
	public void onInitialize() {
		System.out.println("Hello Fabric world!");
		ClickGui.INSTANCE.hudElements.add(new TextElement("hello"));
	}


	@Override
	public void onInitializeClient() {}
}
