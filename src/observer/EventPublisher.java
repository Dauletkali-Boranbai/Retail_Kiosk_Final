package observer;

import observer.events.KioskEvent;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/** Subject: listeners subscribe to an event type and only receive events of that type. */
public class EventPublisher {
    private final Map<Class<? extends KioskEvent>, List<KioskListener<?>>> listeners = new ConcurrentHashMap<>();

    public <E extends KioskEvent> void subscribe(Class<E> type, KioskListener<E> listener) {
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(listener, "listener");
        listeners.computeIfAbsent(type, t -> new CopyOnWriteArrayList<>()).add(listener);
    }

    public <E extends KioskEvent> void unsubscribe(Class<E> type, KioskListener<E> listener) {
        List<KioskListener<?>> registered = listeners.get(type);
        if (registered != null) registered.remove(listener);
    }

    @SuppressWarnings("unchecked")
    public <E extends KioskEvent> void publish(E event) {
        for (KioskListener<?> listener : listeners.getOrDefault(event.getClass(), List.of())) {
            ((KioskListener<E>) listener).onEvent(event);
        }
    }
}
