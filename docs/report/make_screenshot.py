"""Renders docs/demo_output.txt as a terminal-style PNG (docs/img/console-output.png)."""
import html
import pathlib

from playwright.sync_api import sync_playwright

ROOT = pathlib.Path(__file__).resolve().parents[2]
text = (ROOT / "docs" / "demo_output.txt").read_text(encoding="utf-8").strip("\n")
rows = []
for line in text.splitlines():
    cls = "h" if line.startswith("---") or line.startswith("===") else ""
    if "FAILED" in line or "LOW STOCK" in line or "DECLINED" in line:
        cls = "w"
    rows.append(f'<div class="{cls}">{html.escape(line) or "&nbsp;"}</div>')
page = ('<html><body style="margin:0;background:#111c27"><div style="padding:18px 22px;width:1100px;'
        'white-space:pre;font:15px/1.5 \'DejaVu Sans Mono\',Menlo,monospace;color:#d6e2ee">'
        '<style>.h{color:#7fc4ff}.w{color:#ffb86b}</style><div style="color:#6b7f92;margin-bottom:6px">$ sh build.sh</div>'
        + "".join(rows) + "</div></body></html>")
with sync_playwright() as p:
    b = p.chromium.launch()
    t = b.new_page(viewport={"width": 1144, "height": 600}, device_scale_factor=2)
    t.set_content(page)
    t.locator("div").first.screenshot(path=str(ROOT / "docs" / "img" / "console-output.png"))
    b.close()
