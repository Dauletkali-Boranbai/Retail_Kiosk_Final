package observer;

import observer.events.KioskEvent;

/** Observer: reacts to one concrete event type, so no instanceof checks are needed. */
public interface KioskListener<E extends KioskEvent> {
    void onEvent(E event);
}
