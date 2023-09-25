import pandas as pd
from tkinter import Tk, Label, Button, filedialog, messagebox, Listbox

# Global variables
primary_key_col = None
file1_data = None
file2_data = None


# Create the GUI
window = Tk()
window.title("Excel File Comparator")

select_file1_button = Button(window, text="Select File 1", command=lambda: select_file(1))
select_file1_button.pack()

select_file2_button = Button(window, text="Select File 2", command=lambda: select_file(2))
select_file2_button.pack()

label = Label(window, text="Select a primary key column:")
label.pack()

listbox = Listbox(window)
listbox.pack()

