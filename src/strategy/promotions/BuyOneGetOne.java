package strategy.promotions;

import builder.Order;
import java.math.BigDecimal;
import strategy.PricingStrategy;

public class BuyOneGetOne implements PricingStrategy {
    private final String sku;

    public BuyOneGetOne(String sku) { this.sku = sku; }

    @Override
    public BigDecimal discount(Order order) {
        return order.getItems().stream()
                .filter(i -> i.product().getSku().equals(sku))
                .map(i -> i.product().getPrice().multiply(BigDecimal.valueOf(i.quantity() / 2)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override public String name() { return "Buy 1 get 1 free (" + sku + ")"; }
}
