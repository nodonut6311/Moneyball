import java.util.List;

public interface ExpenseOperations {
    void addExpense(Expense expense, List<Budget> budgets);
    List<Expense> loadUserExpenses();
    double calculateTotal(List<Expense> expenses);
    void showCategoryReport(List<Expense> expenses);
    void showMonthlyReport(List<Expense> expenses);
    void showVisualSummary(List<Expense> expenses);
    void showAllReports(List<Expense> expenses);
    void showTopCategories(List<Expense> expenses);
}
