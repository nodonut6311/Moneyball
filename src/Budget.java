// src/Budget.java
import java.io.*;
import java.util.*;

// Abstract base class for budgets
abstract class BudgetBase {
    protected String username;
    protected String category;
    protected double limit;

    public BudgetBase(String username, String category, double limit) {
        this.username = username;
        this.category = category;
        this.limit = limit;
    }

    // Abstract method to enforce a file string representation
    public abstract String toFileString();

    // Abstract method for display
    public abstract void display();
}

public class Budget extends BudgetBase {
    private static final String BUDGET_FILE = "data/budgets.txt";

    // Constructor
    public Budget(String username, String category, double limit) {
        super(username, category, limit);
    }

    // Overloaded constructor with default limit
    public Budget(String username, String category) {
        this(username, category, 0.0);
    }

    // Getters
    public String getUsername() { return username; }
    public String getCategory() { return category; }
    public double getLimit() { return limit; }

    // Method overloading: set new limit
    public void setLimit(double limit) { this.limit = limit; }

    // Overridden abstract method
    @Override
    public String toFileString() {
        return username + "," + category + "," + limit;
    }

    // Overridden abstract method
    @Override
    public void display() {
        System.out.printf("%-10s | %-10s | ₹%.2f%n", username, category, limit);
    }

    // Load all budgets for a specific user
    public static List<Budget> loadBudgets(String username) {
        List<Budget> list = new ArrayList<>();
        File file = new File(BUDGET_FILE);
        if(!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if(line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if(parts.length != 3) continue;
                String user = parts[0];
                String cat = parts[1];
                double limit = Double.parseDouble(parts[2]);
                if(user.equals(username)) {
                    list.add(new Budget(user, cat, limit));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println(ConsoleColors.RED + "Error reading budgets: " + e.getMessage() + ConsoleColors.RESET);
        }
        return list;
    }

    // Save or update a budget
    public static void saveOrUpdateBudget(Budget budget) {
        List<String> lines = new ArrayList<>();
        File file = new File(BUDGET_FILE);

        try {
            if(file.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        if(parts.length != 3) continue;
                        if(parts[0].equals(budget.getUsername()) && parts[1].equalsIgnoreCase(budget.getCategory())) continue;
                        lines.add(line);
                    }
                }
            } else {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            lines.add(budget.toFileString());

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for(String l : lines) {
                    writer.write(l);
                    writer.newLine();
                }
            }

            System.out.println(ConsoleColors.GREEN + "Budget saved/updated successfully!" + ConsoleColors.RESET);

        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "Error saving budget: " + e.getMessage() + ConsoleColors.RESET);
        }
    }
}
