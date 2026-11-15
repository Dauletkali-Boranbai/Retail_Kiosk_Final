package decorator.addons;

import decorator.Product;
import java.util.Objects;

public abstract class ProductDecorator implements Product {
    protected final Product wrapped;

    protected ProductDecorator(Product wrapped) {
        this.wrapped = Objects.requireNonNull(wrapped, "wrapped product");
    }

    @Override public String getSku() { return wrapped.getSku(); }
}
