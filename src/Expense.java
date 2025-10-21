import java.io.Serializable;


public class Expense implements Serializable {
    private String username;
    private String date;
    private String category;
    private double amount;
    private String description;

    public Expense(String username, String date, String category, double amount, String description) {
        this.username = username;
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.description = description;
    }

    public String toFileString() {
        return username + "," + date + "," + category + "," + amount + "," + description;
    }

    public static Expense fromFileString(String line) {
        String[] parts = line.split(",", 5);
        return new Expense(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]), parts[4]);
    }

    @Override
    public String toString() {
        return String.format("%s | %s | ₹%.2f | %s", date, category, amount, description);
    }

    public String getUsername() { return username; }
    public String getCategory() { return category; }
    public double getAmount() { return amount; }
    public String getDate() { return date; }
}
