// src/Expense.java
import java.io.*;
import java.util.Objects;

public class Expense implements Serializable, Savable {
    private String username;
    private String date;      // YYYY-MM-DD
    private String category;
    private double amount;
    private String description;

    // Constructor
    public Expense(String username, String date, String category, double amount, String description) {
        this.username = username;
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.description = description;
    }

    // Overloaded constructor: without description
    public Expense(String username, String date, String category, double amount) {
        this(username, date, category, amount, "No description");
    }

    // Encapsulation: getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    // Convert to file string for compatibility
    public String toFileString() {
        return username + "," + date + "," + category + "," + amount + "," + description;
    }

    // Parse from file
    public static Expense fromFileString(String line) {
        String[] parts = line.split(",", 5);
        return new Expense(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]), parts[4]);
    }

    @Override
    public String toString() {
        return String.format("%s | %s | ₹%.2f | %s", date, category, amount, description);
    }

    // --- Implement Savable interface ---
    @Override
    public boolean save() {
        File dir = new File("data");
        if(!dir.exists()) dir.mkdirs();

        String filename = "data/expense_" + username + ".dat";
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename, true))) {
            out.writeObject(this);
            printSaveStatus(true);
            return true;
        } catch (IOException e) {
            System.err.println(ConsoleColors.RED + "Error saving expense: " + e.getMessage() + ConsoleColors.RESET);
            printSaveStatus(false);
            return false;
        }
    }

    // Equality based on all fields
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Expense)) return false;
        Expense e = (Expense) o;
        return Double.compare(e.amount, amount) == 0 &&
                username.equals(e.username) &&
                date.equals(e.date) &&
                category.equals(e.category) &&
                Objects.equals(description, e.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, date, category, amount, description);
    }
}
