# Retail Store Kiosk

Final project for SE-2513 Design Patterns. Team: Boranbai Dauletkali, Kantarbayev Alibek, Tikzhanov Aldiyar.

A console model of a retail kiosk: build an order, apply a promotion, pay (card, cash or QR), update stock and a sales dashboard. Java 17, no external libraries.

## Run

```sh
sh build.sh
```

Without `sh`: compile everything under `src` into `out` with `javac --release 17`, then run `java -cp out Main`. In IntelliJ mark `src` as Sources Root and run `Main.main()`.
`Main` runs six scenarios and throws if a result is wrong (four sales, $1828.80 revenue, declined card and out-of-stock orders leave stock unchanged, unsubscribe works). Expected output: `docs/demo_output.txt`.

## Patterns

| Package | Pattern | Category | Implemented by |
|---|---|---|---|
| `builder` | Builder | Creational | Kantarbayev Alibek |
| `strategy` | Strategy | Behavioral | Kantarbayev Alibek |
| `factory` | Factory Method | Creational | Boranbai Dauletkali |
| `facade` | Facade | Structural | Boranbai Dauletkali |
| `decorator` | Decorator | Structural | Tikzhanov Aldiyar |
| `observer` | Observer | Behavioral | Tikzhanov Aldiyar |

`Main.java` is the only class outside the six pattern packages.

## Documentation

- `docs/Retail_Kiosk_Report.pdf` - final report.
- `docs/uml/` - Mermaid sources (`.mmd`) and rendered PNG diagrams of every pattern and the checkout sequence.
- `docs/report/` - report sources; `python3 docs/report/build_report.py` rebuilds the PDF (needs Python with Playwright).
