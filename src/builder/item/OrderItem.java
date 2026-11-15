package builder.item;

import decorator.Product;
import java.math.BigDecimal;

/** One line of an order: a (possibly decorated) product and its quantity. */
public record OrderItem(Product product, int quantity) {
    public OrderItem {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be > 0");
    }

    public BigDecimal lineTotal() { return product.getPrice().multiply(BigDecimal.valueOf(quantity)); }
}
