package factory.methods;

import factory.PaymentMethod;
import java.math.BigDecimal;

public abstract class AbstractPaymentMethod implements PaymentMethod {

    @Override
    public final boolean pay(BigDecimal amount) {
        if (amount == null || amount.signum() < 0) {
            throw new IllegalArgumentException("Amount must be present and nonnegative");
        }
        return doPay(amount);
    }

    protected abstract boolean doPay(BigDecimal amount);
}
