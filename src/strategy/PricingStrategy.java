package strategy;

import builder.Order;
import java.math.BigDecimal;

public interface PricingStrategy {
    BigDecimal discount(Order order);
    String name();
}
