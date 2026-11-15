package builder;

import builder.item.OrderItem;
import decorator.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderBuilder {
    private static final AtomicInteger SEQ = new AtomicInteger(1000);

    private String customer = "guest";
    private final List<OrderItem> items = new ArrayList<>();

    public OrderBuilder customer(String name) {
        this.customer = name;
        return this;
    }

    public OrderBuilder addItem(Product product, int quantity) {
        items.add(new OrderItem(product, quantity));
        return this;
    }

    public Order build() {
        if (items.isEmpty()) throw new IllegalStateException("Order has no items");
        return new Order("ORD-" + SEQ.incrementAndGet(), customer, items);
    }
}
