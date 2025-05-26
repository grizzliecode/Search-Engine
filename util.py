import os
import re
import csv
from collections import Counter
from PyPDF2 import PdfReader

def extract_text_from_pdf(pdf_path):
    text = ""
    try:
        reader = PdfReader(pdf_path)
        for page in reader.pages:
            text += page.extract_text() or ""
    except Exception as e:
        print(f"Failed to read {pdf_path}: {e}")
    return text

def extract_words(text):
    return re.findall(r'\w+', text.lower())

def save_word_counts_to_csv(word_counts, output_file='words.csv'):
    with open(output_file, 'w', newline='', encoding='utf-8') as csvfile:
        writer = csv.writer(csvfile)
        writer.writerow(['word', 'frequency'])
        for word, freq in word_counts.items():
            writer.writerow([word, freq])
    print(f"Saved to {output_file}")

def main(pdf_dir):
    all_text = ""
    for filename in os.listdir(pdf_dir):
        if filename.lower().endswith('.pdf'):
            path = os.path.join(pdf_dir, filename)
            print(f"Reading {path}")
            all_text += extract_text_from_pdf(path)
    
    words = extract_words(all_text)
    word_counts = Counter(words)
    save_word_counts_to_csv(word_counts)

if __name__ == "__main__":
    main(pdf_dir=".")
