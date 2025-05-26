import sys
import tkinter as tk
from tkinter import scrolledtext

def main():
    if len(sys.argv) < 2:
        print("Usage: python show_text_window.py \"Your text here\"")
        sys.exit(1)
    text_to_show = sys.argv[1]
    root = tk.Tk()
    root.title("Display Text")
    txt = scrolledtext.ScrolledText(root, width=60, height=20)
    txt.pack(padx=10, pady=10)
    txt.insert(tk.END, text_to_show)
    txt.configure(state='disabled')
    root.mainloop()

if __name__ == "__main__":
    main()
