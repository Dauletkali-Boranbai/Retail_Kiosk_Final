package observer.subscribers;

import observer.KioskListener;
import observer.events.SaleEvent;
import java.math.BigDecimal;

/** ConcreteObserver: live sales dashboard. */
public class SalesDashboard implements KioskListener<SaleEvent> {
    private int sales;
    private BigDecimal revenue = BigDecimal.ZERO;

    @Override
    public void onEvent(SaleEvent s) {
        sales++;
        revenue = revenue.add(s.total());
        System.out.printf(java.util.Locale.US,
                "  [Dashboard] %s by %s via %s: $%.2f | total sales=%d, revenue=$%.2f%n",
                s.orderId(), s.customer(), s.paymentName(), s.total(), sales, revenue);
    }

    public int getSales() { return sales; }
    public BigDecimal getRevenue() { return revenue; }
}
