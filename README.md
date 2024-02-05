# Android Barcode Scanner with Firestore Backend

This Android barcode scanner features a backend using Firestore to scan items and retrieve their prices in the associated shop.

## Building the Project

1. Clone the project:
   ```bash
   git clone https://github.com/5wixy/Scanner.git
Create a Firestore project and move google_services.json into Scanner/app/.

Follow the instructions below to build the project.

## Project Description
The primary purpose of this project is to scan items and return their prices in the shop where the application is used. We leverage the capabilities of the ZXing scanner, and credit is given to the ZXing project for their scanner integration.

Usage Instructions
Navigate through the menu.

Pressing the "Scan" button opens the scanner page. Point the mobile camera at an item's barcode:

It returns the price of the scanned item.
Alternatively, it opens a page to add the item to the list.
Pressing a list item opens a page showing the entire item list, including names, prices, and addition/edit dates.

Long-pressing an item in the list allows for editing.

Swiping left removes an item.

Scrolling to the top and beyond refreshes the item list.

### Disclaimer
This project is created as a hobby and is not intended for commercial use. It is developed for personal enjoyment and learning purposes.

## Contributing
I greatly appreciate contributions of any kind to this project. If you are willing to contribute,Feel free to do so. Have fun!
