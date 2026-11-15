package decorator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BasicProduct implements Product {
    private final String sku;
    private final String name;
    private final BigDecimal price;

    public BasicProduct(String sku, String name, BigDecimal price) {
        if (price == null || price.signum() < 0) throw new IllegalArgumentException("Price must be >= 0");
        this.sku = sku;
        this.name = name;
        this.price = price.setScale(2, RoundingMode.HALF_UP);
    }

    @Override public String getSku() { return sku; }
    @Override public String getName() { return name; }
    @Override public BigDecimal getPrice() { return price; }
}
