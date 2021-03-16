Retail Demo Application
========================
POS
---
The POS tab emulates a POS terminal and allows you to make sales of goods. Pressing the SCAN button simulates the reading of the product by a barcode scanner. In this case, the goods are selected in random order. You can change the quantity and price of a product by clicking the "..." button in the product line. You can also manually enter the desired barcode and immediately indicate the required quantity of goods. The generated check can be paid by pressing "DONE / PAY CHECK" or canceled by pressing "CANCEL / CLEAR CHECK".

BackOffice
----------
The BackOffice tab allows you to search, view, add, modify and delete goods. Also, there you can see what groups of goods and units of measurement of goods are.

Reports
--------
On the REPORTS tab it is possible to see sales by day in the form of a chart and in the form of a report of sales by groups and dates within a certain period.

Backend part
============
The backend part was implemented in Java using Spring Web and Spring Data JPA. H2 is used as a database.
GitHub: https://github.com/d-y-frolov/retail-backend

Frontend part
=============
The frontend part is made in Javascript as a single page application using React. Redux is used as a centralized store.
GitHub: https://github.com/d-y-frolov/retail-frontend