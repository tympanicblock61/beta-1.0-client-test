package net.fabricmc.cryptic;

import net.fabricmc.api.ModInitializer;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Main implements ModInitializer {
	@Override
	public void onInitialize() {
		System.out.println("Hello Fabric world!");
		NbtElement el = new NbtList();
		try {
			PrintStream stream = new PrintStream("test.txt");
			el.method_22717("test", stream);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
