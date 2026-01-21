
import java.util.*;
import java.util.stream.Collectors;

public class VisualSummary {

    public static void display(List<Expense> expenses) {
        if (expenses == null || expenses.isEmpty()) {
            System.out.println("No expenses available to display summary.");
            return;
        }

        // Group expenses by category and sum up their amounts
        Map<String, Double> categoryTotals = expenses.stream()
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        Collectors.summingDouble(Expense::getAmount)
                ));

        double maxAmount = Collections.max(categoryTotals.values());

        System.out.println("\nExpense Summary (Category-wise)");
        System.out.println("-------------------------------------");

        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            String category = entry.getKey();
            double amount = entry.getValue();


            int barLength = (int) ((amount / maxAmount) * 20);
            String bar = "█".repeat(Math.max(1, barLength));

            System.out.printf("%-12s | %-20s ₹%.2f%n", category, bar, amount);
        }

        System.out.println("-------------------------------------\n");
    }
}

