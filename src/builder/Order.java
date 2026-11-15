package builder;

import builder.item.OrderItem;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Order {
    private final String id;
    private final String customer;
    private final List<OrderItem> items;

    Order(String id, String customer, List<OrderItem> items) {
        this.id = id;
        this.customer = customer;
        this.items = List.copyOf(items);
    }

    public String getId() { return id; }
    public String getCustomer() { return customer; }
    public List<OrderItem> getItems() { return items; }

    public Map<String, Long> quantitiesBySku() {
        Map<String, Long> quantities = new LinkedHashMap<>();
        for (OrderItem item : items) {
            quantities.merge(item.product().getSku(), (long) item.quantity(), Long::sum);
        }
        return quantities;
    }

    public BigDecimal getSubtotal() {
        return items.stream().map(OrderItem::lineTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int getItemCount() {
        return items.stream().mapToInt(OrderItem::quantity).sum();
    }
}
