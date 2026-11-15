package strategy.promotions;

import builder.Order;
import java.math.BigDecimal;
import strategy.PricingStrategy;

public class BundleDiscount implements PricingStrategy {
    private final String skuA;
    private final String skuB;
    private final BigDecimal amountOff;

    public BundleDiscount(String skuA, String skuB, BigDecimal amountOff) {
        this.skuA = skuA;
        this.skuB = skuB;
        this.amountOff = amountOff.setScale(2, java.math.RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal discount(Order order) {
        return (has(order, skuA) && has(order, skuB)) ? amountOff : BigDecimal.ZERO;
    }

    private boolean has(Order order, String sku) {
        return order.getItems().stream().anyMatch(i -> i.product().getSku().equals(sku));
    }

    @Override public String name() { return "Bundle " + skuA + " + " + skuB + " (-" + amountOff + ")"; }
}
