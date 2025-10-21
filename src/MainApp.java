import java.util.*;

public class MainApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String username = null;

        System.out.println(ConsoleColors.BOLD + "==== Expense Tracker ====" + ConsoleColors.RESET);
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.print("Choose: ");
        int startChoice = sc.nextInt();
        sc.nextLine();

        if (startChoice == 1) {
            System.out.print("Enter username: ");
            username = sc.nextLine();
            System.out.print("Enter password: ");
            String password = sc.nextLine();

            if (!Login.auth(username, password)) {
                System.out.println(ConsoleColors.RED + "Invalid login. Exiting." + ConsoleColors.RESET);
                return;
            }
        } else if (startChoice == 2) {
            System.out.print("Choose a username: ");
            username = sc.nextLine();
            System.out.print("Choose a password: ");
            String password = sc.nextLine();
            if (!Login.register(username, password)) return;
        } else {
            System.out.println(ConsoleColors.RED + "Invalid option. Exiting." + ConsoleColors.RESET);
            return;
        }

        System.out.println(ConsoleColors.GREEN + "Welcome, " + username + "!" + ConsoleColors.RESET);

        while (true) {
            System.out.println(ConsoleColors.YELLOW + "\n--- MENU ---" + ConsoleColors.RESET);
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. View Total");
            System.out.println("4. View Category Report");
            System.out.println("5. View Monthly Report");
            System.out.println("6. Logout");
            System.out.print("Choose: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Date (YYYY-MM-DD): ");
                    String date = sc.nextLine();
                    System.out.print("Category: ");
                    String category = sc.nextLine();
                    System.out.print("Amount: ");
                    double amount = sc.nextDouble(); sc.nextLine();
                    System.out.print("Description: ");
                    String desc = sc.nextLine();

                    Expense exp = new Expense(username, date, category, amount, desc);
                    ExpenseManager.addExpense(exp);
                    System.out.println(ConsoleColors.GREEN + " Expense added!" + ConsoleColors.RESET);
                    break;

                case 2:
                    List<Expense> expenses = ExpenseManager.loadExpenses(username);
                    System.out.println(ConsoleColors.CYAN + "--- Your Expenses ---" + ConsoleColors.RESET);
                    expenses.forEach(System.out::println);
                    break;

                case 3:
                    double total = ExpenseManager.calculateTotal(ExpenseManager.loadExpenses(username));
                    System.out.println(ConsoleColors.BLUE + " Total spent: ₹" + total + ConsoleColors.RESET);
                    break;

                case 4:
                    ExpenseManager.showCategoryReport(ExpenseManager.loadExpenses(username));
                    break;

                case 5:
                    ExpenseManager.showMonthlyReport(ExpenseManager.loadExpenses(username));
                    break;

                case 6:
                    System.out.println(ConsoleColors.GREEN + "Logged out successfully." + ConsoleColors.RESET);
                    return;

                default:
                    System.out.println(ConsoleColors.RED + "Invalid option." + ConsoleColors.RESET);
            }
        }
    }
}
