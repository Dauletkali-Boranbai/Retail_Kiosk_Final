package factory.creators;

import factory.PaymentCreator;
import factory.PaymentMethod;
import factory.methods.CardPayment;

public final class CardPaymentCreator implements PaymentCreator {
    @Override
    public PaymentMethod createPayment() {
        return new CardPayment();
    }
}
