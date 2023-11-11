package net.fabricmc.cryptic.events.types;

import net.fabricmc.cryptic.events.Event;

public class MouseEvent extends Event {
    public static class Click extends MouseEvent {
        private static final Click INSTANCE = new Click();
        public int mouseX;
        public int mouseY;
        public int button;

        public static Event get(int mouseX, int mouseY, int button) {
            INSTANCE.mouseX = mouseX;
            INSTANCE.mouseY = mouseY;
            INSTANCE.button = button;
            return INSTANCE;
        }
    }

    public static class Release extends Click {}
}
