package observer.events;

import java.math.BigDecimal;

public record SaleEvent(String orderId, String customer, BigDecimal total,
                        String paymentName, int itemCount) implements KioskEvent { }
