package facade;

import java.math.BigDecimal;

public record CheckoutResult(boolean success, BigDecimal total, String message) { }
