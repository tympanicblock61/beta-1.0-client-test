package net.fabricmc.cryptic.events.types;

import de.florianmichael.dietrichevents2.AbstractEvent;
import net.fabricmc.cryptic.events.IEvent;
import net.fabricmc.cryptic.utils.datatypes.Vec2i;

public interface MouseEventListener extends IEvent {

    void onClick(Vec2i pos, int button);
    void onRelease(Vec2i pos, int button);
    void onDrag(Vec2i from, Vec2i to, int button);

    enum Action {
        Click,
        Release,
        Drag
    }

    public static class Event extends AbstractEvent<MouseEventListener> {
        public static final int ID = 1;

        private final Vec2i from;
        private final Vec2i to;
        private final int button;
        private final Action action;

        public Event(Vec2i from, Vec2i to, int button, Action action) {
            this.from = from;
            this.to = to;
            this.button = button;
            this.action = action;
        }
        @Override
        public void call(MouseEventListener listener) {
            if (action == Action.Click) listener.onClick(from, button);
            else if (action == Action.Release) listener.onRelease(from, button);
            else if (action == Action.Drag) listener.onDrag(from, to, button);
        }
    }
}
