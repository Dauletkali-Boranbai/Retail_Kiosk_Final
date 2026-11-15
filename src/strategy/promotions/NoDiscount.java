package strategy.promotions;

import builder.Order;
import java.math.BigDecimal;
import strategy.PricingStrategy;

public class NoDiscount implements PricingStrategy {
    @Override public BigDecimal discount(Order order) { return BigDecimal.ZERO; }
    @Override public String name() { return "No promotion"; }
}
