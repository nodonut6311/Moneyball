// src/ExpenseManager.java
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ExpenseManager implements ExpenseOperations {

    private String username;
    private static final String EXPENSE_FILE = "data/expenses.txt";

    // Constructor
    public ExpenseManager(String username) {
        this.username = username;
    }

    // Add expense with budget check
    @Override
    public void addExpense(Expense expense, List<Budget> budgets) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EXPENSE_FILE, true))) {
            writer.write(expense.toFileString());
            writer.newLine();
            System.out.println(ConsoleColors.GREEN + "Expense added successfully!" + ConsoleColors.RESET);
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "Error saving expense: " + e.getMessage() + ConsoleColors.RESET);
            return;
        }

        checkBudgets(expense, budgets);
    }

    // Load all expenses for this user
    @Override
    public List<Expense> loadUserExpenses() {
        List<Expense> list = new ArrayList<>();
        File file = new File(EXPENSE_FILE);
        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(EXPENSE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Expense e = Expense.fromFileString(line);
                if (e.getUsername().equals(username)) list.add(e);
            }
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "Error reading expense file: " + e.getMessage() + ConsoleColors.RESET);
        }
        return list;
    }

    // Calculate total amount
    @Override
    public double calculateTotal(List<Expense> expenses) {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }

    // Show category-wise report
    @Override
    public void showCategoryReport(List<Expense> expenses) {
        Map<String, Double> totals = expenses.stream()
                .collect(Collectors.groupingBy(Expense::getCategory, Collectors.summingDouble(Expense::getAmount)));

        System.out.println(ConsoleColors.CYAN + "\n--- Total by Category ---" + ConsoleColors.RESET);
        totals.forEach((cat, amt) -> System.out.printf("%-15s : ₹%.2f%n", cat, amt));
    }

    // Show monthly report safely
    @Override
    public void showMonthlyReport(List<Expense> expenses) {
        Map<String, Double> monthlyTotals = new TreeMap<>();
        for (Expense e : expenses) {
            String month = extractMonth(e.getDate());
            if (month == null) continue;
            monthlyTotals.put(month, monthlyTotals.getOrDefault(month, 0.0) + e.getAmount());
        }

        System.out.println(ConsoleColors.PURPLE + "\n--- Monthly Summary ---" + ConsoleColors.RESET);
        monthlyTotals.forEach((month, amt) -> System.out.printf("%-10s : ₹%.2f%n", month, amt));
    }

    // Show visual summary
    @Override
    public void showVisualSummary(List<Expense> expenses) {
        VisualSummary.display(expenses);
    }

    // Show combined reports
    @Override
    public void showAllReports(List<Expense> expenses) {
        System.out.println(ConsoleColors.YELLOW + "\n===== Expense Reports =====" + ConsoleColors.RESET);
        double total = calculateTotal(expenses);
        System.out.printf(ConsoleColors.GREEN + "\nTotal Spending: ₹%.2f%n" + ConsoleColors.RESET, total);
        showCategoryReport(expenses);
        showMonthlyReport(expenses);
        showVisualSummary(expenses);
        System.out.println(ConsoleColors.YELLOW + "===========================\n" + ConsoleColors.RESET);
    }

    // Show top 3 spending categories
    @Override
    public void showTopCategories(List<Expense> expenses) {
        if (expenses.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW + "No expenses to analyze." + ConsoleColors.RESET);
            return;
        }

        Map<String, Double> totals = expenses.stream()
                .collect(Collectors.groupingBy(Expense::getCategory, Collectors.summingDouble(Expense::getAmount)));

        List<Map.Entry<String, Double>> sorted = totals.entrySet().stream()
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
                .limit(3)
                .toList();

        double totalSpending = calculateTotal(expenses);

        System.out.println(ConsoleColors.PURPLE + "\n--- Top 3 Spending Categories ---" + ConsoleColors.RESET);
        for (Map.Entry<String, Double> e : sorted) {
            double percent = (e.getValue() / totalSpending) * 100;
            System.out.printf("%-15s : ₹%.2f (%.1f%% of total)%n", e.getKey(), e.getValue(), percent);
        }
    }

    // --- Private helper methods ---
    private void checkBudgets(Expense expense, List<Budget> budgets) {
        if (budgets.isEmpty()) return;

        List<Expense> userExpenses = loadUserExpenses();
        userExpenses.add(expense);
        String expenseMonth = extractMonth(expense.getDate());
        if (expenseMonth == null) return;

        for (Budget b : budgets) {
            if (b.getCategory().equalsIgnoreCase("ALL")) {
                double totalMonthly = userExpenses.stream()
                        .filter(e -> expenseMonth.equals(extractMonth(e.getDate())))
                        .mapToDouble(Expense::getAmount)
                        .sum();
                if (totalMonthly > b.getLimit()) {
                    double over = totalMonthly - b.getLimit();
                    System.out.println(ConsoleColors.RED +
                            "⚠️ You’ve exceeded your total monthly budget by ₹" + over + " for " + expenseMonth + ConsoleColors.RESET);
                }
            } else {
                double totalCategory = userExpenses.stream()
                        .filter(e -> e.getCategory().equalsIgnoreCase(b.getCategory()))
                        .filter(e -> expenseMonth.equals(extractMonth(e.getDate())))
                        .mapToDouble(Expense::getAmount)
                        .sum();
                if (totalCategory > b.getLimit()) {
                    double over = totalCategory - b.getLimit();
                    System.out.println(ConsoleColors.RED +
                            "⚠️ You’ve exceeded your " + b.getCategory() + " budget by ₹" + over + " for " + expenseMonth + ConsoleColors.RESET);
                }
            }
        }
    }

    // Safe month extractor from date string
    private String extractMonth(String date) {
        if (date == null || date.length() < 7) return null; // prevents StringIndexOutOfBounds
        return date.substring(0, 7);
    }
}
