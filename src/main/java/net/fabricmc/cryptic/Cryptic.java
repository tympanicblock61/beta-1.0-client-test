package net.fabricmc.cryptic;

import de.florianmichael.dietrichevents2.DietrichEvents2;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.cryptic.events.types.KeyEventListener;
import net.fabricmc.cryptic.events.types.TickEventListener;
import net.fabricmc.cryptic.gui.Category;
import net.fabricmc.cryptic.gui.modules.KillAura;
import net.fabricmc.cryptic.gui.modules.testmodule1;
import net.fabricmc.cryptic.gui.modules.testmodule2;
import net.fabricmc.cryptic.gui.screens.ClickGui;
import net.fabricmc.cryptic.gui.screens.ElementPicker;
import net.fabricmc.cryptic.gui.screens.HudEditor;
import net.fabricmc.cryptic.gui.elements.HealthBar;
import net.fabricmc.cryptic.gui.elements.TextElement;
import net.fabricmc.cryptic.gui.screens.SettingsScreen;
import net.fabricmc.cryptic.utils.KeybindUtils;
import net.fabricmc.cryptic.utils.LanguageTranslations;
import net.fabricmc.cryptic.utils.datatypes.Vec2i;
import net.minecraft.client.Minecraft;
import net.fabricmc.cryptic.gui.Module;
import java.util.Arrays;
import java.util.Random;
import static net.fabricmc.cryptic.utils.KeybindUtils.Key;

@SuppressWarnings("unchecked")
public class Cryptic implements ModInitializer, ClientModInitializer, TickEventListener, KeyEventListener {
	public static Minecraft mc = Minecraft.getMinecraft();
	private HealthBar playerHealth;
	public static DietrichEvents2 EventBus = new DietrichEvents2(100);
	public static LanguageTranslations translator = new LanguageTranslations();
	public static boolean loaded = false;
	@Override
	public void onInitialize() {
		System.out.println("Hello Fabric world!");
		HudEditor.INSTANCE.elements.add(new TextElement("hello"));
		HudEditor.INSTANCE.elements.add(new TextElement("test2"));
		ElementPicker.INSTANCE.elements.put("TextElement", TextElement.class);
		ElementPicker.INSTANCE.elements.put("HealthBar", HealthBar.class);
		playerHealth = new HealthBar(Vec2i.create(200, 100), Vec2i.create(100, 10), 0, 20);
		HudEditor.INSTANCE.elements.add(playerHealth);
		EventBus.subscribe(TickEventListener.Event.ID, this);
		EventBus.subscribe(KeyEventListener.Event.ID, this);
		addCategories();
	}

	@Override
	public void onInitializeClient() {
		ClickGui.INSTANCE.init();
		ElementPicker.INSTANCE.init();
		HudEditor.INSTANCE.init();
		SettingsScreen.INSTANCE.init();
		loaded = true;
	}

	public void addCategories() {
		Category Misc = new Category(translator.translate("category::misc::name"), translator.translate("category::misc::desc"), Vec2i.create(100,100));
		Misc.modules.add(KillAura.INSTANCE);
		Misc.modules.add(testmodule1.INSTANCE);
		Misc.modules.add(testmodule2.INSTANCE);
		ClickGui.INSTANCE.categories.add(Misc);
	}

	@Override
	public void onPre() {
	}

	@Override
	public void onPost() {
		if (mc.playerEntity != null) {
			playerHealth.percent = Math.max(0, mc.playerEntity.method_2600()) * playerHealth.one_percent;
		}
	}

	@Override
	public void onPress(char eventChar, int eventKey) {
		Key key = Key.getByCode(eventKey);
		if (key != Key.None) {
			for (Category category : ClickGui.INSTANCE.categories) {
				for (Module module : category.modules) {
					if (module.Keybind == key) {
						module.active = !module.active;
						module.onActivate();
					}
				}
			}
		}
	}

	@Override
	public void onRelease(char eventChar, int eventKey) {}
}