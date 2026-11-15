package strategy;

import builder.Order;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/** Strategy context: stateless, the pricing rule is passed in for every calculation. */
public class PriceCalculator {

    public BigDecimal finalTotal(Order order, PricingStrategy strategy) {
        Objects.requireNonNull(strategy, "strategy");
        BigDecimal total = order.getSubtotal().subtract(strategy.discount(order)).max(BigDecimal.ZERO);
        return total.setScale(2, RoundingMode.HALF_UP);
    }
}
