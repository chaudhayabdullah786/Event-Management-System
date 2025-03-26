public class Budget {
    private String eventId;        // Stores event ID
    private String budgetName;  // Name of the budget
    private double totalAmount; // Total budget amount
    private double usedAmount;  // Amount used

    // Constructor
    public Budget(String eventId, String budgetName, double totalAmount ,double usedAmount) {
        this.eventId = eventId;
        this.budgetName = budgetName;
        this.totalAmount = Math.max(totalAmount, 0); // Prevent negative budget
        this.usedAmount = usedAmount;
    }

    public Budget(String eventId, String budgetName, double totalAmount ) {
        this.eventId = eventId;
        this.budgetName = budgetName;
        this.totalAmount = Math.max(totalAmount, 0); // Prevent negative budget
        this.usedAmount = 0;
    } 
    // Getters
    public String getEventId() {
        return eventId;
    }

    public String getBudgetName() {
        return budgetName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getUsedAmount() {
        return usedAmount;
    }

    public double getRemainingBudget() {
        return totalAmount - usedAmount;
    }

    // Setters
    public void setTotalAmount(double totalAmount) {
        if (totalAmount >= usedAmount) {
            this.totalAmount = totalAmount;
        } else {
            System.out.println("Error: Total budget cannot be less than used amount.");
        }
    }

    public void setBudgetName(String budgetName) {
        this.budgetName = budgetName;
    }

    // Method to add expenses
    public boolean addExpense(double expense) {
        if (expense < 0 || usedAmount + expense > totalAmount) {
            System.out.println("Error: Not enough budget for this expense.");
            return false;
        }
        usedAmount += expense;
        return true;
    }

    @Override
    public String toString() {
        return "Budget Details:\n" +
               "Budget: " + budgetName + "\n" +
               "Total Amount: $" + totalAmount + "\n" +
               "Used Amount: $" + usedAmount + "\n" +
               "Remaining Amount: $" + getRemainingBudget() + "\n" +
               "Linked Event ID: " + eventId + "\n";
    }
}
