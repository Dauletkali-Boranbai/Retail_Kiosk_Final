package decorator.addons;

import decorator.Product;
import java.math.BigDecimal;

public class GiftWrap extends ProductDecorator {
    private static final BigDecimal FEE = new BigDecimal("3.00");

    public GiftWrap(Product wrapped) { super(wrapped); }

    @Override public String getName() { return wrapped.getName() + " + gift wrap"; }
    @Override public BigDecimal getPrice() { return wrapped.getPrice().add(FEE); }
}
