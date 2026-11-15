package strategy.promotions;

import builder.Order;
import java.math.BigDecimal;
import java.math.RoundingMode;
import strategy.PricingStrategy;

public class PercentageDiscount implements PricingStrategy {
    private final int percent;

    public PercentageDiscount(int percent) {
        if (percent < 0 || percent > 100) throw new IllegalArgumentException("percent must be 0..100");
        this.percent = percent;
    }

    @Override public BigDecimal discount(Order order) {
        return order.getSubtotal().multiply(BigDecimal.valueOf(percent))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }
    @Override public String name() { return percent + "% off"; }
}
