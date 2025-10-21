# Expense Tracker (Java Terminal App)

A simple **terminal-based expense tracking application** built in Java.  
Allows users to **register, login, add expenses, view expenses, and see reports**. Data is stored in simple text files.  

---

## 📁 Project Structure
```bash
ExpenseTracker/
│
├── data/
│   ├── users.txt        # Stores username,password
│   └── expenses.txt     # Stores expense records
│
└── src/
    ├── Login.java
    ├── Expense.java
    ├── ExpenseManager.java
    ├── MainApp.java
    ├── Utils.java
    └── ConsoleColors.java

```
---

##  Features (So far (v1)):

- User registration and login  
- Add, view, and track expenses  
- Total spending report  
- Category-wise and monthly summary  
- Colorized terminal output for better readability  

---

## Compilation & Run

1. Navigate to project folder:

```bash
cd path/to/Moneyball
```
2. Compile all Java File : 
```bash
javac src/*.java
```
3. Running the App :
```bash
java -cp src MainApp
```
---
## Data Files format : 
1. users.txt :
```bash
alex,pass123
bella,qwerty
carlos,abc123
diana,letmein
ethan,secure1
fiona,hello2025
gabriel,xyz987
harper,password
isaac,moonlight
jade,summer21
```
2. expenses.txt(username,date,category,amount,description) :
```bash
alex,2025-10-20,Food,320,Lunch at cafe
bella,2025-10-21,Transport,120,Taxi ride
carlos,2025-10-19,Groceries,450,Weekly groceries
diana,2025-10-18,Entertainment,250,Movie tickets
ethan,2025-10-20,Food,180,Snacks and drinks
fiona,2025-10-21,Utilities,130,Electricity bill
gabriel,2025-10-19,Transport,90,Bus fare
harper,2025-10-18,Food,400,Dinner with friends
isaac,2025-10-20,Health,220,Pharmacy purchase
jade,2025-10-21,Shopping,350,Clothes
```
---



