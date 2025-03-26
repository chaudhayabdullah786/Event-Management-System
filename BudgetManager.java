import java.io.*;
import java.util.*;

public class BudgetManager {
    private List<Budget> budgets;
    private static final String BUDGET_FILE = "budgets.txt"; // File for saving budgets

    //  Constructor (Load budgets from file)
    public BudgetManager() {
        this.budgets = new ArrayList<>();
        loadBudgetsFromFile();
    }

    //  Add a Budget
    public boolean addBudget(String eventId, String budgetName, double totalAmount ) {
        Budget budget = new Budget(eventId, budgetName, totalAmount );
        budgets.add(budget);
        saveBudgetsToFile(); // Automatically save
        System.out.println("Budget added successfully.");
        return true;
    }

    // Save Budgets to File
    private void saveBudgetsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BUDGET_FILE))) {
            for (Budget budget : budgets) {
                writer.write(budget.getEventId() + "," + budget.getBudgetName() + "," +
                             budget.getTotalAmount() + "," + budget.getUsedAmount());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving budgets: " + e.getMessage());
        }
    }

    //  Load Budgets from File
    private void loadBudgetsFromFile() {
        File file = new File(BUDGET_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(BUDGET_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String eventId = parts[0];
                    String budgetName = parts[1];
                    double totalAmount = Double.parseDouble(parts[2]);
                    double usedAmount = Double.parseDouble(parts[3]);

                    Budget budget = new Budget(eventId, budgetName, totalAmount , usedAmount);
                    budget.addExpense(usedAmount); // Restore used amount safely
                    budgets.add(budget);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading budgets: " + e.getMessage());
        }
    }

    //  Add Expense to Budget (Auto-save after modification)
    public void addExpenseToBudget(String budgetName, double expense) {
        for (Budget budget : budgets) {
            if (budget.getBudgetName().equalsIgnoreCase(budgetName)) {
                if (budget.addExpense(expense)) {
                    saveBudgetsToFile(); // Automatically save
                    System.out.println("Expense added and budget saved automatically.");
                }
                return;
            }
        }
        System.out.println("Budget not found.");
    }

    //  Update Budget Amount (Auto-save after modification)
    public boolean updateBudgetAmount(String budgetName, double newAmount) {
        for (Budget budget : budgets) {
            if (budget.getBudgetName().equalsIgnoreCase(budgetName)) {
                budget.setTotalAmount(newAmount);
                saveBudgetsToFile(); // Automatically save
                System.out.println("Budget updated and saved automatically.");
                return true;
            }
        }
        System.out.println("Budget not found.");
        return false;
    }

    //  Remove Budget (Auto-save after modification)
    public void removeBudget(String budgetName) {
        boolean removed = budgets.removeIf(budget -> budget.getBudgetName().equalsIgnoreCase(budgetName));
        if (removed) {
            saveBudgetsToFile();
            System.out.println("Budget removed and saved automatically.");
        } else {
            System.out.println("Budget not found.");
        }
    }

    //  Get Remaining Budget
    public double getRemainingBudget(String budgetName) {
        for (Budget budget : budgets) {
            if (budget.getBudgetName().equalsIgnoreCase(budgetName)) {
                return budget.getRemainingBudget();
            }
        }
        throw new NoSuchElementException("Budget not found.");
    }

        //  Get Budget by Name
        public Budget getBudgetByName(String budgetName) {
            for (Budget budget : budgets) {
                if (budget.getBudgetName().equalsIgnoreCase(budgetName)) {
                    return budget; // Return the Budget object if found
                }
            }
            return null; // Return null if not found
        }
    

    //  Display Budgets
    public void displayBudgets() {
        if (budgets.isEmpty()) {
            System.out.println("No budgets available.");
        } else {
            for (Budget budget : budgets) {
                System.out.println(budget);
            }
        }
    }
}
