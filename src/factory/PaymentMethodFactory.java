package factory;

import java.util.Map;

public final class PaymentMethodFactory {
    private final Map<PaymentType, PaymentCreator> creators;

    public PaymentMethodFactory(Map<PaymentType, PaymentCreator> creators) {
        this.creators = Map.copyOf(creators);
    }

    public PaymentMethod create(PaymentType type) {
        PaymentCreator creator = creators.get(type);
        if (creator == null) {
            throw new IllegalArgumentException("Unsupported payment type: " + type);
        }
        return creator.createPayment();
    }
}
