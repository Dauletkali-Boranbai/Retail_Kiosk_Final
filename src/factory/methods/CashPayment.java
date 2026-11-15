package factory.methods;

import java.math.BigDecimal;

public class CashPayment extends AbstractPaymentMethod {
    @Override protected boolean doPay(BigDecimal amount) { return true; }
    @Override public String name() { return "Cash"; }
}
