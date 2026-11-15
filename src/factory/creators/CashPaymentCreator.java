package factory.creators;

import factory.PaymentCreator;
import factory.PaymentMethod;
import factory.methods.CashPayment;

public final class CashPaymentCreator implements PaymentCreator {
    @Override
    public PaymentMethod createPayment() {
        return new CashPayment();
    }
}
