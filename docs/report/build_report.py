"""Builds docs/Retail_Kiosk_Report.pdf from docs/report/sections/*.html.

Code blocks are written as <pre class="code" data-src="src/..." data-title="..."></pre>
and are filled from the real source files, so the report always matches the code.
Requires: python3 and playwright (chromium).   Usage: python3 docs/report/build_report.py
"""
import html
import pathlib
import re

from playwright.sync_api import sync_playwright

ROOT = pathlib.Path(__file__).resolve().parents[2]
REPORT = ROOT / "docs" / "report"
OUT_PDF = ROOT / "docs" / "Retail_Kiosk_Report.pdf"

CODE = re.compile(r'<pre class="code"([^>]*)></pre>')


def fill_code(match):
    attrs = dict(re.findall(r'data-(\w+)="([^"]*)"', match.group(1)))
    lines = (ROOT / attrs["src"]).read_text(encoding="utf-8").splitlines()
    if "lines" in attrs:
        start, end = (int(x) for x in attrs["lines"].split("-"))
        lines = lines[start - 1:end]
    title = attrs.get("title", pathlib.Path(attrs["src"]).name)
    body = html.escape("\n".join(lines))
    return f'<div class="codebox"><div class="codetitle">{html.escape(title)}</div><pre>{body}</pre></div>'


def build():
    parts = [p.read_text(encoding="utf-8") for p in sorted((REPORT / "sections").glob("*.html"))]
    page = "\n".join(parts)
    page = CODE.sub(fill_code, page)
    css = (REPORT / "style.css").read_text(encoding="utf-8")
    doc = f'<!doctype html><html><head><meta charset="utf-8"><title>Retail Store Kiosk - Final Project Report</title><style>{css}</style></head><body>{page}</body></html>'
    tmp = REPORT / "_report.html"          # next to the sections so ../uml and ../img resolve
    tmp.write_text(doc, encoding="utf-8")
    try:
        with sync_playwright() as p:
            browser = p.chromium.launch()
            tab = browser.new_page()
            tab.goto(tmp.as_uri())
            tab.wait_for_load_state("networkidle")
            footer = ('<div style="font-size:8px;width:100%;padding:0 16mm;color:#5b6b7b;display:flex;justify-content:space-between">'
                      '<span>Retail Store Kiosk | SE-2513 Design Patterns</span>'
                      '<span>Page <span class="pageNumber"></span> / <span class="totalPages"></span></span></div>')
            tab.pdf(path=str(OUT_PDF), format="A4", print_background=True, display_header_footer=True,
                    header_template="<div></div>", footer_template=footer,
                    margin={"top": "18mm", "bottom": "20mm", "left": "16mm", "right": "16mm"})
            browser.close()
    finally:
        tmp.unlink(missing_ok=True)
    print("written", OUT_PDF)


if __name__ == "__main__":
    build()
