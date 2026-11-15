package facade.subsystem.inventory;

import java.util.HashMap;
import java.util.Map;


public class StockRepository {
    private final Map<String, Integer> stock = new HashMap<>();

    public void put(String sku, int quantity) { stock.put(sku, quantity); }
    public int get(String sku) { return stock.getOrDefault(sku, 0); }
}
