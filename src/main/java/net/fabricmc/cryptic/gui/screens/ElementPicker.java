package net.fabricmc.cryptic.gui.screens;

import net.fabricmc.cryptic.gui.Element;
import net.fabricmc.cryptic.gui.components.ScrollableList;
import net.fabricmc.cryptic.utils.datatypes.Vec2i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ElementPicker extends Screen {

    public static ElementPicker INSTANCE = new ElementPicker();
    public Vec2i clickPos = null;
    public LinkedHashMap<String, Class<? extends Element>> elements = new LinkedHashMap<>();
    ScrollableList list = null;

    @Override
    public void init() {
        Minecraft mc = Minecraft.getMinecraft();
        list = new ScrollableList((mc.width/2)/2, 50, 100 ,200, 10, Color.BLACK, Color.GRAY, new ArrayList<>(elements.keySet()), (string)->{
            System.out.println("scrollable list click string callback");
            System.out.println(string);
            System.out.println(elements.get(string));
            Class<? extends Element> element = elements.get(string);
            try {
                Element elem = element.getDeclaredConstructor(Vec2i.class, Vec2i.class).newInstance(clickPos, Vec2i.create(100, 100));
                System.out.println(elem);
                HudEditor.INSTANCE.elements.add(elem);
                mc.currentScreen = null;
                mc.closeScreen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void render(int mouseX, int mouseY, float tickDelta) {
        list.setStrings(new ArrayList<>(elements.keySet()));
        list.render();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        list.mouseClicked(mouseX, mouseY, button);
    }


    @Override
    public boolean shouldPauseGame() {
        return false;
    }
}