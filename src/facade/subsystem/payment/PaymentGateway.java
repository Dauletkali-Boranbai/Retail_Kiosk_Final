package facade.subsystem.payment;

import factory.PaymentMethod;
import java.math.BigDecimal;

/** Abstraction the facade depends on (DIP): any payment backend can replace PaymentProcessor. */
public interface PaymentGateway {
    boolean process(PaymentMethod method, BigDecimal amount);
}
