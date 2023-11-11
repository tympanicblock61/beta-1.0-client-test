package net.fabricmc.cryptic.events.types;

import net.fabricmc.cryptic.events.Event;

public class TickEvent extends Event {
    public static class Pre extends TickEvent {
        private static final Pre INSTANCE = new Pre();
        public static Pre get() {
            return INSTANCE;
        }
    }

    public static class Post extends TickEvent {
        private static final Post INSTANCE = new Post();

        public static Post get() {
            return INSTANCE;
        }
    }
}