package observer.subscribers;

import observer.KioskListener;
import observer.events.LowStockEvent;

/** ConcreteObserver: reacts only to low-stock events. */
public class StockAlertLogger implements KioskListener<LowStockEvent> {
    @Override
    public void onEvent(LowStockEvent e) {
        System.out.println("  [StockAlert] LOW STOCK: " + e.sku() + " (" + e.remaining() + " left)");
    }
}
