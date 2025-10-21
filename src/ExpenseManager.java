import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ExpenseManager {
    private static final String EXPENSE_FILE = "data/expenses.txt";

    public static void addExpense(Expense expense) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EXPENSE_FILE, true))) {
            writer.write(expense.toFileString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "Error saving expense: " + e.getMessage() + ConsoleColors.RESET);
        }
    }

    public static List<Expense> loadExpenses(String username) {
        List<Expense> list = new ArrayList<>();
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

    public static double calculateTotal(List<Expense> expenses) {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }

    public static void showCategoryReport(List<Expense> expenses) {
        Map<String, Double> totals = expenses.stream()
                .collect(Collectors.groupingBy(Expense::getCategory, Collectors.summingDouble(Expense::getAmount)));

        System.out.println(ConsoleColors.CYAN + "\n--- Total by Category ---" + ConsoleColors.RESET);
        totals.forEach((cat, amt) -> System.out.printf("%-15s : ₹%.2f%n", cat, amt));
    }

    public static void showMonthlyReport(List<Expense> expenses) {
        Map<String, Double> monthlyTotals = new TreeMap<>();
        for (Expense e : expenses) {
            String month = e.getDate().substring(0, 7); // yyyy-MM
            monthlyTotals.put(month, monthlyTotals.getOrDefault(month, 0.0) + e.getAmount());
        }

        System.out.println(ConsoleColors.PURPLE + "\n--- Monthly Summary ---" + ConsoleColors.RESET);
        monthlyTotals.forEach((month, amt) -> System.out.printf("%-10s : ₹%.2f%n", month, amt));
    }
}
