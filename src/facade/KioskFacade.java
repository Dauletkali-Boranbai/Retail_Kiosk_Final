package facade;

import builder.Order;
import java.math.BigDecimal;
import java.util.Map;
import facade.subsystem.inventory.StockService;
import facade.subsystem.payment.PaymentGateway;
import facade.subsystem.payment.TransactionLog;
import factory.PaymentMethod;
import factory.PaymentMethodFactory;
import factory.PaymentType;
import observer.EventPublisher;
import observer.events.LowStockEvent;
import observer.events.SaleEvent;
import strategy.PriceCalculator;
import strategy.PricingStrategy;


public class KioskFacade {
    private final PriceCalculator priceCalculator;
    private final PaymentMethodFactory paymentFactory;
    private final PaymentGateway paymentProcessor;
    private final TransactionLog transactionLog;
    private final StockService inventory;
    private final EventPublisher publisher;

    public KioskFacade(PriceCalculator priceCalculator, PaymentMethodFactory paymentFactory,
                       PaymentGateway paymentProcessor, TransactionLog transactionLog,
                       StockService inventory,
                       EventPublisher publisher) {
        this.priceCalculator = priceCalculator;
        this.paymentFactory = paymentFactory;
        this.paymentProcessor = paymentProcessor;
        this.transactionLog = transactionLog;
        this.inventory = inventory;
        this.publisher = publisher;
    }

    public CheckoutResult checkout(Order order, PaymentType type, PricingStrategy promotion) {
        Map<String, Long> quantities = order.quantitiesBySku();
        if (!inventory.isAvailable(quantities)) {
            return new CheckoutResult(false, BigDecimal.ZERO, "Out of stock");
        }
        BigDecimal total = priceCalculator.finalTotal(order, promotion);
        PaymentMethod method = paymentFactory.create(type);
        boolean approved = paymentProcessor.process(method, total);
        transactionLog.record(method.name(), total, approved);
        if (!approved) {
            return new CheckoutResult(false, total, method.name() + " payment declined");
        }
        inventory.reduceStock(quantities);
        publisher.publish(new SaleEvent(order.getId(), order.getCustomer(), total,
                method.name(), order.getItemCount()));
        for (String sku : quantities.keySet()) {
            if (inventory.isLow(sku)) {
                publisher.publish(new LowStockEvent(sku, inventory.remaining(sku)));
            }
        }
        return new CheckoutResult(true, total, "OK");
    }
}
