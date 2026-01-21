# Expense Tracker (Java Terminal App)

A simple **terminal-based expense tracking application** built in Java.  
Allows users to **register, login, add expenses, view expenses, and see reports**. Data is stored in simple text files.  

---

## 📁 Project Structure
```bash
Moneyball/
│
├── data/
│   ├── users.txt
│   ├── expenses.txt       
│   └── budgets.txt       
│
└── src/
    ├── MainApp.java
    ├── Login.java
    ├── Expense.java
    ├── ExpenseManager.java
    ├── ExpenseOperations.java
    ├── Budget.java
    ├── VisualSummary.java
    ├── Utils.java
    ├── ConsoleColors.java
    ├── Authenticatable.java
    └── Savable.java
```
---

##  Features (So far (v1)):

User registration and secure authentication (Login, Authenticatable)

Add, view, and manage expenses (Expense, ExpenseManager, ExpenseOperations)

Persistent storage of user data using text files (Savable)

Category-wise, monthly, and total expense summaries

Budget creation and budget limit validation per category (Budget)

Visual expense summaries displayed in the terminal (VisualSummary)

Colorized terminal output for improved readability (ConsoleColors)

Utility-based input handling and formatting (Utils)

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
3. budgets.txt(username,category,amount) :
```bash
alex,Food,1000
bella,Transport,800
carlos,Groceries,1200
diana,Entertainment,500
ethan,Food,500
fiona,Utilities,400
gabriel,Transport,300
harper,Food,600
isaac,Health,500
jade,Shopping,1000
Ramm,Food,2000
Ramm,Shopping,4000
Ravi,Food,500
Ravi,Groceries,1000
Ravi,ALL,2000
Praba,Food,1.0
Ramm,ALL,5000.0
Ramm,Foos,7000.0
```
---



