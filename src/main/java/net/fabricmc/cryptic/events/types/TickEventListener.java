package net.fabricmc.cryptic.events.types;

import de.florianmichael.dietrichevents2.AbstractEvent;
import net.fabricmc.cryptic.events.IEvent;

public interface TickEventListener extends IEvent {
    void onPre();
    void onPost();

    enum Type {
        Pre,
        Post;
    }

    public static class Event extends AbstractEvent<TickEventListener> {
        public static final int ID = 2;
        private final Type type;

        public Event(Type type) {
            this.type = type;
        }

        @Override
        public void call(TickEventListener listener) {
            if (type == Type.Pre) listener.onPre();
            else if (type == Type.Post) listener.onPost();
        }
    }
}