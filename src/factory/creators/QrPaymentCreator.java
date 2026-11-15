package factory.creators;

import factory.PaymentCreator;
import factory.PaymentMethod;
import factory.methods.QrPayment;

public final class QrPaymentCreator implements PaymentCreator {
    @Override
    public PaymentMethod createPayment() {
        return new QrPayment();
    }
}
