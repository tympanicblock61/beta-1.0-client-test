package net.fabricmc.cryptic.events.types;

import net.fabricmc.cryptic.events.Event;

public class KeyEvent extends Event {

    public static class Press extends KeyEvent {
        private static final Press INSTANCE = new Press();
        public char eventChar;
        public int eventKey;

        public static KeyEvent get(char eventChar, int eventKey) {
            INSTANCE.eventChar = eventChar;
            INSTANCE.eventKey = eventKey;
            return INSTANCE;
        }
    }

    public static class Release extends Press {}
}
