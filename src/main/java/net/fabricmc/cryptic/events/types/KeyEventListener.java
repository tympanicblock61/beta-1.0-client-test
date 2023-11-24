package net.fabricmc.cryptic.events.types;

import de.florianmichael.dietrichevents2.AbstractEvent;
import net.fabricmc.cryptic.events.IEvent;

public interface KeyEventListener extends IEvent {

    void onPress(char eventChar, int eventKey);

    void onRelease(char eventChar, int eventKey);


    class Event extends AbstractEvent<KeyEventListener> {
        public static final int ID = 0;
        private final char eventChar;
        private final int eventKey;
        private final Action action;

        public Event(char eventChar, int eventKey, Action action) {
            this.eventChar = eventChar;
            this.eventKey = eventKey;
            this.action = action;
        }

        @Override
        public void call(KeyEventListener listener) {
            if (action == Action.Press) listener.onPress(eventChar, eventKey);
            else if (action == Action.Release) listener.onRelease(eventChar, eventKey);
        }
    }

    enum Action {
        Press,
        Release;
    }
}
