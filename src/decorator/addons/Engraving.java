package decorator.addons;

import decorator.Product;
import java.math.BigDecimal;

public class Engraving extends ProductDecorator {
    private static final BigDecimal FEE = new BigDecimal("5.00");

    public Engraving(Product wrapped) { super(wrapped); }

    @Override public String getName() { return wrapped.getName() + " + engraving"; }
    @Override public BigDecimal getPrice() { return wrapped.getPrice().add(FEE); }
}
