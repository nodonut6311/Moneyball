import java.util.*;

public class MainApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String username = null;

        System.out.println(ConsoleColors.BOLD + "==== Expense Tracker ====" + ConsoleColors.RESET);
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.print("Choose: ");

        int startChoice = 0;
        try {
            startChoice = sc.nextInt();
        } catch(InputMismatchException e) {
            System.out.println(ConsoleColors.RED + "Invalid input. Exiting." + ConsoleColors.RESET);
            return;
        }
        sc.nextLine();

        if (startChoice == 1) {
            System.out.print("Enter username: ");
            username = sc.nextLine();
            System.out.print("Enter password: ");
            String password = sc.nextLine();

            if (!Login.staticAuth(username, password)) {
                System.out.println(ConsoleColors.RED + "Invalid login. Exiting." + ConsoleColors.RESET);
                return;
            }
        } else if (startChoice == 2) {
            System.out.print("Choose a username: ");
            username = sc.nextLine();
            System.out.print("Choose a password: ");
            String password = sc.nextLine();

            if (!Login.staticRegister(username, password)) return;
        } else {
            System.out.println(ConsoleColors.RED + "Invalid option. Exiting." + ConsoleColors.RESET);
            return;
        }

        // Load existing budgets immediately after login
        List<Budget> userBudgets = Budget.loadBudgets(username);

        // Create ExpenseManager instance (Object, Abstraction)
        ExpenseOperations manager = new ExpenseManager(username);

        System.out.println(ConsoleColors.GREEN + "Welcome, " + username + "!" + ConsoleColors.RESET);

        while (true) {
            System.out.println(ConsoleColors.YELLOW + "\n--- MENU ---" + ConsoleColors.RESET);
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. View Total");
            System.out.println("4. View Category Report");
            System.out.println("5. View Monthly Report");
            System.out.println("6. View Visual Summary");
            System.out.println("7. View All Reports");
            System.out.println("8. Set Budget");
            System.out.println("9. View Top 3 Spending Categories");
            System.out.println("10. Logout");
            System.out.print("Choose: ");

            int choice = 0;
            try {
                choice = sc.nextInt();
            } catch(InputMismatchException e) {
                System.out.println(ConsoleColors.RED + "Invalid input. Try again." + ConsoleColors.RESET);
                sc.nextLine();
                continue;
            }
            sc.nextLine();

            List<Expense> userExpenses = ((ExpenseManager) manager).loadUserExpenses();

            switch (choice) {
                case 1: // Add Expense
                    try {
                        System.out.print("Date (YYYY-MM-DD): ");
                        String date = sc.nextLine();
                        System.out.print("Category: ");
                        String category = sc.nextLine();

                        if(category.equalsIgnoreCase("ALL")) {
                            System.out.println(ConsoleColors.RED + "⚠️ 'ALL' cannot be used as an expense category!" + ConsoleColors.RESET);
                            break;
                        }

                        System.out.print("Amount: ");
                        double amount = sc.nextDouble(); sc.nextLine();
                        System.out.print("Description: ");
                        String desc = sc.nextLine();

                        Expense exp = new Expense(username, date, category, amount, desc);
                        manager.addExpense(exp, userBudgets); // Pass budgets for check
                    } catch(InputMismatchException e) {
                        System.out.println(ConsoleColors.RED + "Invalid amount entered." + ConsoleColors.RESET);
                        sc.nextLine();
                    }
                    break;

                case 2: // View Expenses
                    System.out.println(ConsoleColors.CYAN + "--- Your Expenses ---" + ConsoleColors.RESET);
                    userExpenses.forEach(System.out::println);
                    break;

                case 3: // View Total
                    double total = manager.calculateTotal(userExpenses);
                    System.out.println(ConsoleColors.BLUE + " Total spent: ₹" + total + ConsoleColors.RESET);
                    break;

                case 4: // Category Report
                    manager.showCategoryReport(userExpenses);
                    break;

                case 5: // Monthly Report
                    manager.showMonthlyReport(userExpenses);
                    break;

                case 6: // Visual Summary
                    manager.showVisualSummary(userExpenses);
                    break;

                case 7: // All Reports
                    manager.showAllReports(userExpenses);
                    break;

                case 8: // Set Budget
                    try {
                        System.out.print("Enter category (or ALL for total monthly limit): ");
                        String cat = sc.nextLine();
                        System.out.print("Enter budget limit: ");
                        double limit = sc.nextDouble(); sc.nextLine();
                        Budget b = new Budget(username, cat, limit);
                        Budget.saveOrUpdateBudget(b);
                        userBudgets = Budget.loadBudgets(username); // Update in-memory
                    } catch(InputMismatchException e) {
                        System.out.println(ConsoleColors.RED + "Invalid limit entered." + ConsoleColors.RESET);
                        sc.nextLine();
                    }
                    break;

                case 9: // Top 3 Spending Categories
                    ((ExpenseManager) manager).showTopCategories(userExpenses);
                    break;

                case 10: // Logout
                    System.out.println(ConsoleColors.GREEN + "Logged out successfully." + ConsoleColors.RESET);
                    return;

                default:
                    System.out.println(ConsoleColors.RED + "Invalid option." + ConsoleColors.RESET);
            }
        }
    }
}
