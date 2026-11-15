package factory.methods;

import java.math.BigDecimal;

public class CardPayment extends AbstractPaymentMethod {
    private static final BigDecimal LIMIT = new BigDecimal("1000.00");

    @Override protected boolean doPay(BigDecimal amount) { return amount.compareTo(LIMIT) <= 0; }
    @Override public String name() { return "Card"; }
}
