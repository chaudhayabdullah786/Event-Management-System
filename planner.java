public class planner {
    private int adminID;
    private String username;
    private String password;

    private ClientManager clientManager;
    private EventManager eventManager;
    private GuestManager guestManager;
    private ResourceManager resourceManager;
    private TaskManager taskManager;

    // Default credentials
    private final String validUsername = "admin";
    private final String validPassword = "1234"; // Example password

    // Constructor
    public planner(int adminID, String username, String password ) {
        this.adminID = adminID;
        this.username = username;
        this.password = password;

        this.clientManager = new ClientManager();
        this.eventManager = new EventManager();
        this.guestManager = new GuestManager();
        this.resourceManager = new ResourceManager();
        this.taskManager = new TaskManager();
    }

    // Login method
    public boolean login(String enteredUsername, String enteredPassword) {
        if (enteredUsername == null || enteredPassword == null) {
            System.out.println("⚠️ Invalid credentials. Username or password cannot be null.");
            return false;
        }
        if (enteredUsername.equals(validUsername) && enteredPassword.equals(validPassword)) {
            System.out.println("✅ Login successful. Welcome, Admin!");
            return true;
        } else {
            System.out.println("❌ Incorrect username or password. Please try again.");
            return false;
        }
    }

    // Approving an event
    public void approveEvent(Event event) {
        if (event != null) {
            event.setApproved(true);
            System.out.println("✅ Event '" + event.getName() + "' has been approved by Admin.");
        } else {
            System.out.println("⚠️ Invalid event. Approval failed.");
        }
    }

    // Managing budget
    public void manageBudget(Budget budget, double newTotal) {
        if (budget != null && newTotal >= 0) {
            budget.setTotalAmount(newTotal);
            System.out.println("💰 Budget updated to: $" + newTotal);
        } else {
            System.out.println("⚠️ Invalid budget update. Ensure budget is not null and amount is non-negative.");
        }
    }

    // Removing a user (event organizer/client)
    public void removeUser(Client user) {
        if (user != null) {
            System.out.println("🗑️ User '" + user.getName() + "' has been removed from the system.");
            // Add logic to remove user from system
        } else {
            System.out.println("⚠️ Invalid user. Removal failed.");
        }
    }
}
