package facade.subsystem.payment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class TransactionLog {
    private final List<String> entries = new ArrayList<>();

    public void record(String methodName, BigDecimal amount, boolean approved) {
        entries.add(String.format(Locale.US, "%s $%.2f -> %s", methodName, amount, approved ? "APPROVED" : "DECLINED"));
    }

    public List<String> entries() { return Collections.unmodifiableList(entries); }
}
