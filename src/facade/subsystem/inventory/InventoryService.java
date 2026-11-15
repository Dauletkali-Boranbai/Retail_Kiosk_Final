package facade.subsystem.inventory;

import java.util.Map;


public class InventoryService implements StockService {
    private final StockRepository repository;
    private final int lowStockThreshold;

    public InventoryService(StockRepository repository, int lowStockThreshold) {
        this.repository = repository;
        this.lowStockThreshold = lowStockThreshold;
    }

    @Override
    public boolean isAvailable(Map<String, Long> quantities) {
        return quantities.entrySet().stream()
                .allMatch(item -> repository.get(item.getKey()) >= item.getValue());
    }

    @Override
    public void reduceStock(Map<String, Long> quantities) {
        quantities.forEach((sku, quantity) ->
                repository.put(sku, repository.get(sku) - Math.toIntExact(quantity)));
    }

    @Override
    public int remaining(String sku) { return repository.get(sku); }

    @Override
    public boolean isLow(String sku) { return repository.get(sku) <= lowStockThreshold; }
}
