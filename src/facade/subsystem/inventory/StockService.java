package facade.subsystem.inventory;

import java.util.Map;

/** Abstraction the facade depends on (DIP): any stock backend can replace InventoryService. */
public interface StockService {
    boolean isAvailable(Map<String, Long> quantities);
    void reduceStock(Map<String, Long> quantities);
    int remaining(String sku);
    boolean isLow(String sku);
}
