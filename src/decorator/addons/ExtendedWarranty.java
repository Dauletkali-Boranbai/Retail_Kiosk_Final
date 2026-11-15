package decorator.addons;

import decorator.Product;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ExtendedWarranty extends ProductDecorator {
    private static final BigDecimal RATE = new BigDecimal("0.15");

    public ExtendedWarranty(Product wrapped) { super(wrapped); }

    @Override public String getName() { return wrapped.getName() + " + 2y warranty"; }
    @Override public BigDecimal getPrice() {
        return wrapped.getPrice().multiply(BigDecimal.ONE.add(RATE)).setScale(2, RoundingMode.HALF_UP);
    }
}
