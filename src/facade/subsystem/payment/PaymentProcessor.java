package facade.subsystem.payment;

import factory.PaymentMethod;
import java.math.BigDecimal;

/** Only processes a payment; auditing is the job of TransactionLog (SRP). */
public class PaymentProcessor implements PaymentGateway {

    @Override
    public boolean process(PaymentMethod method, BigDecimal amount) {
        return method.pay(amount);
    }
}
