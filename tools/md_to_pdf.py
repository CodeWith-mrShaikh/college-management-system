# Simple Markdown to PDF converter using markdown and weasyprint
# Requires: pip install markdown weasyprint

import sys
from pathlib import Path
import markdown
from weasyprint import HTML

def md_to_pdf(md_path, pdf_path):
    md = Path(md_path).read_text(encoding='utf-8')
    html = markdown.markdown(md, extensions=['tables', 'fenced_code'])
    HTML(string=html).write_pdf(pdf_path)

if __name__ == '__main__':
    if len(sys.argv) < 3:
        print('Usage: python md_to_pdf.py input.md output.pdf')
        sys.exit(2)
    md_to_pdf(sys.argv[1], sys.argv[2])
