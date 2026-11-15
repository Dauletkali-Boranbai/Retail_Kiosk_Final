package observer.events;

public record LowStockEvent(String sku, int remaining) implements KioskEvent { }
