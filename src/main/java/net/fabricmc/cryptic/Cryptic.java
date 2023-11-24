package net.fabricmc.cryptic;

import de.florianmichael.dietrichevents2.DietrichEvents2;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.cryptic.events.IEvent;
import net.fabricmc.cryptic.events.types.TickEventListener;
import net.fabricmc.cryptic.gui.HudEditor;
import net.fabricmc.cryptic.gui.hudelements.HealthBar;
import net.fabricmc.cryptic.gui.hudelements.TextElement;
import net.fabricmc.cryptic.utils.MobEntityWrapper;
import net.fabricmc.cryptic.utils.datatypes.Vec2i;
import net.minecraft.client.Minecraft;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class Cryptic implements ModInitializer, TickEventListener {
	public static Minecraft mc = Minecraft.getMinecraft();
	private MobEntityWrapper player = null;
	private HealthBar playerHealth;
	public static DietrichEvents2 EventBus = new DietrichEvents2(100);

	@Override
	public void onInitialize() {
		System.out.println("Hello Fabric world!");
		HudEditor.INSTANCE.hudElements.add(new TextElement("hello"));
		HudEditor.INSTANCE.hudElements.add(new TextElement("test2"));
		playerHealth = new HealthBar(Vec2i.create(200, 100), Vec2i.create(100, 10), 0, 20);
		HudEditor.INSTANCE.hudElements.add(playerHealth);
		EventBus.subscribe(Event.ID, this);
		Map<Class<?>, List<Class<?>>> EventsAndListeners = getEventsAndListeners(Cryptic.class.getPackage().getName(), IEvent.class);
		System.out.println(EventsAndListeners);
		for (Class<?> key : EventsAndListeners.keySet()) {
			List<Class<?>> eventListeners = EventsAndListeners.get(key);
			if (eventListeners.isEmpty()) continue;
			for (Class<?> eventListener : eventListeners) {
				List<Class<?>> events = Arrays.stream(eventListener.getDeclaredClasses()).collect(Collectors.toList());
				if (!events.isEmpty()) {
					for (Class<?> event : events) {
						List<String> names = Arrays.stream(event.getDeclaredFields()).map((Field::getName)).collect(Collectors.toList());
						if (names.contains("ID")) {
							try {
								Field id = event.getDeclaredField("ID");
								id.setAccessible(true);
								Field instance = key.getDeclaredField("INSTANCE");
								instance.setAccessible(true);
								EventBus.subscribe(id.getInt(event), instance.get(key));
								System.out.println("subscribed "+key.getSimpleName()+" for event "+eventListener.getSimpleName());
							} catch (NoSuchFieldException | IllegalAccessException ignored) {}
						}
					}
				}
			}
		}
	}

	public static Map<Class<?>, List<Class<?>>> getEventsAndListeners(String packageName, Class<?> baseClass) {
		Map<Class<?>, List<Class<?>>> implementingClasses = new HashMap<>();
		Reflections reflections = new Reflections(new ConfigurationBuilder().forPackages(packageName).setScanners(Scanners.SubTypes));
		Set<Class<?>> subClasses = (Set<Class<?>>) reflections.getSubTypesOf(baseClass);
		for (Class<?> subClass : subClasses) {
			List<Class<?>> implementedInterfaces = new ArrayList<>();
			Class<?>[] interfaces = subClass.getInterfaces();
			for (Class<?> interfaceClass : interfaces) {
				if (!interfaceClass.equals(baseClass)) {
					implementedInterfaces.add(interfaceClass);
				}
			}
			implementingClasses.put(subClass, implementedInterfaces);
		}
		return implementingClasses;
	}

	@Override
	public void onPre() {
	}

	@Override
	public void onPost() {
		if (mc.playerEntity != null) {
			if (player == null) player = new MobEntityWrapper(mc.playerEntity);
			playerHealth.percent = player.getHealth() * playerHealth.one_percent;
			System.out.println(playerHealth.percent);
		}
	}
}
