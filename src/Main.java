import builder.Order;
import builder.OrderBuilder;
import builder.OrderDirector;
import decorator.BasicProduct;
import decorator.Product;
import decorator.addons.Engraving;
import decorator.addons.ExtendedWarranty;
import decorator.addons.GiftWrap;
import facade.CheckoutResult;
import facade.KioskFacade;
import facade.subsystem.inventory.InventoryService;
import facade.subsystem.inventory.StockRepository;
import facade.subsystem.payment.PaymentProcessor;
import facade.subsystem.payment.TransactionLog;
import factory.PaymentMethodFactory;
import factory.PaymentType;
import factory.creators.CardPaymentCreator;
import factory.creators.CashPaymentCreator;
import factory.creators.QrPaymentCreator;
import observer.EventPublisher;
import observer.events.LowStockEvent;
import observer.events.SaleEvent;
import observer.subscribers.SalesDashboard;
import observer.subscribers.StockAlertLogger;
import strategy.PriceCalculator;
import strategy.PricingStrategy;
import strategy.promotions.BundleDiscount;
import strategy.promotions.BuyOneGetOne;
import strategy.promotions.NoDiscount;
import strategy.promotions.PercentageDiscount;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Product laptop = new BasicProduct("LAP", "Laptop", usd("800"));
        Product mouse = new BasicProduct("MOU", "Mouse", usd("25"));
        Product headphones = new BasicProduct("HEA", "Headphones", usd("60"));
        Product coffee = new BasicProduct("COF", "Coffee", usd("4"));

        StockRepository repo = new StockRepository();
        repo.put("LAP", 5);
        repo.put("MOU", 10);
        repo.put("HEA", 3);
        repo.put("COF", 20);

        EventPublisher publisher = new EventPublisher();
        SalesDashboard dashboard = new SalesDashboard();
        publisher.subscribe(SaleEvent.class, dashboard);
        publisher.subscribe(LowStockEvent.class, new StockAlertLogger());

        TransactionLog log = new TransactionLog();
        PriceCalculator calculator = new PriceCalculator();
        PaymentMethodFactory paymentFactory = new PaymentMethodFactory(Map.of(
                PaymentType.CARD, new CardPaymentCreator(),
                PaymentType.CASH, new CashPaymentCreator(),
                PaymentType.QR, new QrPaymentCreator()));
        KioskFacade kiosk = new KioskFacade(calculator, paymentFactory,
                new PaymentProcessor(), log, new InventoryService(repo, 2), publisher);

        OrderDirector director = new OrderDirector();

        run(kiosk, "1. Laptop with warranty + mouse, card, no promo",
                new OrderBuilder().customer("Aigerim")
                        .addItem(new ExtendedWarranty(laptop), 1).addItem(mouse, 1).build(),
                new NoDiscount(), PaymentType.CARD);

        run(kiosk, "2. 2x gift-wrapped headphones, cash, 20% off (triggers low stock)",
                director.singleItemOrder("Nurlan", new GiftWrap(headphones), 2),
                new PercentageDiscount(20), PaymentType.CASH);

        run(kiosk, "3. 4x coffee, QR, buy-one-get-one",
                director.singleItemOrder("Dana", coffee, 4),
                new BuyOneGetOne("COF"), PaymentType.QR);

        run(kiosk, "4. Laptop + mouse bundle, card",
                director.pairOrder("Timur", laptop, mouse),
                new BundleDiscount("LAP", "MOU", usd("50")), PaymentType.CARD);

        run(kiosk, "5. 2 laptops by card (declined: over card limit)",
                director.singleItemOrder("Sara", laptop, 2),
                new NoDiscount(), PaymentType.CARD);

        run(kiosk, "6. Headphones on two lines, one gift-wrapped and engraved (out of stock)",
                new OrderBuilder().customer("Ali").addItem(headphones, 1)
                        .addItem(new GiftWrap(new Engraving(headphones)), 1).build(),
                new NoDiscount(), PaymentType.CASH);

        if (dashboard.getSales() != 4 || dashboard.getRevenue().compareTo(usd("1828.80")) != 0
                || repo.get("LAP") != 3 || repo.get("HEA") != 1 || log.entries().size() != 5) {
            throw new IllegalStateException("Unexpected checkout results");
        }

        publisher.unsubscribe(SaleEvent.class, dashboard);
        publisher.publish(new SaleEvent("ORD-TEST", "nobody", BigDecimal.ONE, "Cash", 1));
        if (dashboard.getSales() != 4) {
            throw new IllegalStateException("Unsubscribed listener was still notified");
        }

        System.out.println("\n=== Transaction log ===");
        log.entries().forEach(e -> System.out.println("  " + e));
        System.out.printf(Locale.US, "%n=== Final dashboard: %d sales, $%.2f revenue ===%n",
                dashboard.getSales(), dashboard.getRevenue());
    }

    private static BigDecimal usd(String amount) { return new BigDecimal(amount); }

    private static void run(KioskFacade kiosk, String title,
                            Order order, PricingStrategy promo, PaymentType type) {
        System.out.println("\n--- " + title + " ---");
        System.out.printf(Locale.US, "  Subtotal $%.2f, promo: %s%n", order.getSubtotal(), promo.name());
        CheckoutResult r = kiosk.checkout(order, type, promo);
        System.out.printf(Locale.US, "  Result: %s ($%.2f) %s%n",
                r.success() ? "SUCCESS" : "FAILED", r.total(), r.message());
    }
}
