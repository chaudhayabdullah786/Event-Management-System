



import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static ClientManager clientManager = new ClientManager();
    private static EventManager eventManager = new EventManager();
    private static ResourceManager resourceManager = new ResourceManager(); 
    private static TaskManager taskManager = new TaskManager();
    private static BudgetManager budgetManager = new BudgetManager();
    private static GuestManager guestManager = new GuestManager();
    
    private static Scanner scanner = new Scanner(System.in);
    private static planner planner = new planner(0, null, null);

    public static void main(String[] args) {
        if (!plannerLogin()) {
            System.out.println("Maximum login attempts reached. Exiting...");
            return;
        } 

        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Manage Clients");
            System.out.println("2. Manage Events");
            System.out.println("3. Manage Resources");
            System.out.println("4. Manage Tasks");
            System.out.println("5. Manage Guests");
            System.out.println("6. Manage Budget");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = getValidInteger("Enter your choice: ");

            switch (choice) {
                case 1:
                    manageClients();
                    break;
                case 2:
                    manageEvents(eventManager);
                    break;
                case 3:
                    manageResources();
                    break;
                case 4:
                    manageTasks(taskManager, resourceManager);
                    break;
                case 5:
                    manageGuests();
                    break;
                case 6:
                    manageBudget();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static boolean plannerLogin() {
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;
    
        while (attempts < MAX_ATTEMPTS) {
            // Prompt for username
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
    
            // Prompt for password
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
    
            // Check if login is successful
            if (planner.login(username, password)) {
                System.out.println("Login successful.");
                return true;
            } else {
                attempts++;
                System.out.println("Incorrect username or password. Attempts left: " + (MAX_ATTEMPTS - attempts));
            }
        }
        return false;
    }

    private static void manageClients() {
        while (true) {
            System.out.println("\nClient Management:");
            System.out.println("1. Add Client");
            System.out.println("2. View Clients");
            System.out.println("3. Update Client");
            System.out.println("4. Delete Client");
            System.out.println("5. Sort Clients by ID");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    String cnic = getValidCNIC("Enter client CNIC (13 digits): ");
                    String name = getValidString("Enter client name: ");
                    String email = getValidEmail("Enter client email: ");
                    clientManager.addClient(cnic, name, email);
                    System.out.println("Client added successfully.");
                    break;

                case 2:
                    clientManager.displayClients();
                    break;

                case 3:
                    String updateCnic = getValidCNIC("Enter client CNIC to update (13 digits): ");
                    String newName = getValidString("Enter new name: ");
                    String newEmail = getValidEmail("Enter new email: ");
                    clientManager.updateClient(updateCnic, newName, newEmail);
                    System.out.println("Client updated successfully.");
                    break;

                case 4:
                    String deleteCnic = getValidCNIC("Enter client CNIC to delete (13 digits): ");
                    clientManager.deleteClient(deleteCnic);
                    System.out.println("Client deleted successfully.");
                    break;

                case 5:
                    clientManager.sortClientsById();
                    System.out.println("Clients sorted by ID.");
                    break;

                case 6:
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void manageEvents(EventManager eventManager) {
        while (true) {
            System.out.println("\n🔹 Event Management Menu:");
            System.out.println("1. Add Event");
            System.out.println("2. View Events");
            System.out.println("3. Update Event");
            System.out.println("4. Delete Event");
            System.out.println("5. Undo");
            System.out.println("6. Redo");
            System.out.println("7. Process Next Event");
            // System.out.println("8. Save Events to File");
            // System.out.println("9. Load Events from File");
            System.out.println("8. Add Client to Event");
            System.out.println("9. Add Task to Event");
            System.out.println("10. Remove Task from Event");
            System.out.println("11. Add Guest to Event");
            System.out.println("12. Remove Guest from Event");
            System.out.println("13. Assign Budget to Event");
            System.out.println("14. Remove Budget from Event");
            System.out.println("15. Exit");
    
            int choice = getValidInteger("Enter your choice: ");
            String eventId;  // Declare once, reuse in cases
            Event event;     // Declare once, reuse in cases
            
            switch (choice) {
                case 1:
                    eventId = getValidString("Enter event ID: ");
                    String name = getValidString("Enter event name: ");
                    LocalDate date = getValidDate("Enter event date (YYYY-MM-DD): ");
                    event = new Event(eventId, name, date.toString(), "");
                    eventManager.addEvent(event);
                    System.out.println(" Event added successfully.");
                    break;
                case 2:
                    eventManager.displayAllEvents();
                    break;
                case 3:
                    eventId = getValidString("Enter event ID to update: ");
                    Event existingEvent = eventManager.getEventById(eventId);
                    if (existingEvent != null) {
                        String newName = getValidString("Enter new name: ");
                        LocalDate newDate = getValidDate("Enter new date (YYYY-MM-DD): ");
                        event = new Event(eventId, newName, newDate.toString(), "");
                        eventManager.updateEvent(event);
                        System.out.println(" Event updated successfully.");
                    } else {
                        System.out.println(" Event ID not found!");
                    }
                    break;
                case 4:
                    eventId = getValidString("Enter event ID to delete: ");
                    eventManager.deleteEvent(eventId);
                    System.out.println(" Event deleted successfully.");
                    break;
                case 5:
                    eventManager.undo();
                    break;
                case 6:
                    eventManager.redo();
                    break;
                case 7:
                    eventManager.processNextEvent();
                    break;
                case 8:
                    System.out.print("Enter event ID: ");
                    eventId = scanner.nextLine();
                    System.out.print("Enter client CNIC: ");
                    String clientCnic = scanner.nextLine();
                    
                    // Retrieve the actual Client object using CNIC
                    Client client = clientManager.getClientByCnic(clientCnic);
                    
                    if (client != null) {
                        eventManager.addClientToEvent(eventId, client);
                        System.out.println("Client added to the event successfully!");
                    } else {
                        System.out.println("Client not found!");
                    }
                    break;
            
                case 9:
                    System.out.print("Enter event ID: ");
                    eventId = scanner.nextLine();
                    System.out.print("Enter task ID: ");
                    int taskId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    
                    // Retrieve the actual Task object using task ID
                    Task task = taskManager.getTaskById(taskId);
                    
                    if (task != null) {
                        eventManager.addTaskToEvent(eventId, task);
                        System.out.println("Task added to the event successfully!");
                    } else {
                        System.out.println("Task not found!");
                    }
                    break;
                case 10:
                    eventId = getValidString("Enter event ID: ");
                    int taskID = getValidInteger("Enter task ID to remove: ");
                    eventManager.removeTaskFromEvent(eventId, taskID);
                    break;
                case 11:
                    eventId = getValidString("Enter event ID: ");
                    String guestCNIC = getValidString("Enter guest CNIC: "); // Declare guestCNIC here
                
                    Guest guest = guestManager.getGuestByCNIC(guestCNIC);
                    eventManager.addGuestToEvent(eventId, guest);
                    break;
                
                case 12:
                    eventId = getValidString("Enter event ID: ");
                    guestCNIC = getValidString("Enter guest CNIC to remove: "); // Reuse declared variable
                    eventManager.removeGuestFromEvent(eventId, guestCNIC);
                    break;
                
                case 13:
                    eventId = getValidString("Enter event ID: ");
                    String budgetName = getValidString("Enter budget name: "); // Asking for budget name
                    
                    eventManager.assignBudgetToEvent(eventId, budgetName); // Passing budgetName instead of Budget object
                    System.out.println("Budget assignment attempted. Check for success messages.");
                    break;
                
                
                case 14:
                    eventId = getValidString("Enter event ID: ");
                    eventManager.removeBudgetFromEvent(eventId);
                    break;
                
                case 15:
                    System.out.println(" Exiting Event Manager.");
                    return;
                default:
                    System.out.println(" Invalid choice, please try again.");
            }
        }
    }
    
    


    private static void manageResources() {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\nResource Management:");
            System.out.println("1. Add Resource");
            System.out.println("2. View Resources");
            System.out.println("3. Update Resource");
            System.out.println("4. Delete Resource");
            System.out.println("5. Sort Resources");
            // System.out.println("6. Save Resources to File");
            // System.out.println("7. Load Resources from File");
            System.out.println("6. Back to Main Menu");

            int choice = getValidInteger("Enter your choice: ");

            switch (choice) {
                case 1:
                    int id = getValidInteger("Enter resource ID: ");
                    String name = getValidString("Enter resource name: ");
                    boolean available = getValidBoolean("Is the resource available? (true/false): ");
                    resourceManager.addResource(new Resource(id, name, available));
                    break;

                case 2:
                    resourceManager.displayResources();
                    break;

                case 3:
                    int updateId = getValidInteger("Enter resource ID to update: ");
                    Resource resourceToUpdate = resourceManager.getResourceById(updateId);
                    if (resourceToUpdate == null) {
                        System.out.println("Resource not found.");
                        break;
                    }
                    String newName = getValidString("Enter new name: ");
                    boolean newAvailable = getValidBoolean("Is the resource available? (true/false): ");
                    resourceToUpdate.updateResource(newName, newAvailable);
                    resourceManager.saveResourcesToFile();
                    System.out.println("Resource updated successfully.");
                    break;

                case 4:
                    int deleteId = getValidInteger("Enter resource ID to delete: ");
                    Resource resourceToDelete = resourceManager.getResourceById(deleteId);
                    if (resourceToDelete == null) {
                        System.out.println("Resource not found.");
                        break;
                    }
                    resourceManager.deleteResource(deleteId);
                    System.out.println("Resource deleted successfully.");
                    break;

                case 5:
                    resourceManager.sortResourcesByHeapSort();
                    break;
                case 6:
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void manageTasks(TaskManager taskManager, ResourceManager resourceManager) {
        while (true) {
            System.out.println("\nTask Management:");
            System.out.println("1. Add Task");
            System.out.println("2. Remove Task");
            System.out.println("3. Search Task");
            System.out.println("4. Update Task");
            System.out.println("5. Mark Task as Completed");
            System.out.println("6. Process Next Task");
            System.out.println("7. Display Tasks");
            System.out.println("8. Add Resource to Task");
            System.out.println("9. Remove Resource from Task");
            System.out.println("10. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = getValidInteger("Enter your choice: ");

            switch (choice) {
                case 1:
                    String description = getValidString("Enter task description: ");
                    String priority = getValidPriority("Enter priority (High, Medium, Low): ");
                    LocalDate dueDate = getValidDate("Enter due date (YYYY-MM-DD): ");
                    String assignee = getValidString("Enter assignee: ");
                    taskManager.addTask(new Task(description, priority, dueDate, assignee));
                    break;

                case 2:
                    int removeId = getValidInteger("Enter task ID to remove: ");
                    taskManager.removeTask(removeId);
                    break;

                case 3:
                    int searchId = getValidInteger("Enter task ID to search: ");
                    Task task = taskManager.searchTask(searchId);
                    System.out.println(task != null ? task : "Task not found.");
                    break;

                case 4:
                    int updateId = getValidInteger("Enter task ID to update: ");
                    String newDescription = getValidString("Enter new description: ");
                    String newPriority = getValidPriority("Enter new priority (High, Medium, Low): ");
                    LocalDate newDueDate = getValidDate("Enter new due date (YYYY-MM-DD): ");
                    String newAssignee = getValidString("Enter new assignee: ");
                    boolean updated = taskManager.updateTask(updateId, newDescription, newPriority, newDueDate, newAssignee);
                    System.out.println(updated ? " Task updated successfully." : "⚠️ Task not found.");
                    break;

                case 5:
                    int completeId = getValidInteger("Enter task ID to mark as completed: ");
                    taskManager.markTaskCompleted(completeId);
                    break;

                case 6:
                    taskManager.processNextTask();
                    break;

                case 7:
                    taskManager.displayTasks();
                    break;

                    case 8: // Add Resource to Task
                    int taskIdToAdd = getValidInteger("Enter task ID to add resource: ");
                    int resourceIdToAdd = getValidInteger("Enter resource ID to add to the task: ");
                    Resource resourceToAdd = resourceManager.getResourceById(resourceIdToAdd); // Fetch resource by ID
                    if (resourceToAdd != null) {
                        taskManager.addResourceToTask(taskIdToAdd, resourceToAdd); // Add resource to task
                        System.out.println("Resource " + resourceToAdd.getId() + " added to task " + taskIdToAdd);
                    } else {
                        System.out.println(" Resource with ID " + resourceIdToAdd + " not found.");
                    }
                    break;

                case 9: // Remove Resource from Task
                    int taskIdToRemove = getValidInteger("Enter task ID to remove resource: ");
                    int resourceIdToRemove = getValidInteger("Enter resource ID to remove from the task: ");
                    Resource resourceToRemove = resourceManager.getResourceById(resourceIdToRemove);
                    if (resourceToRemove != null) {
                        taskManager.removeResourceFromTask(taskIdToRemove, resourceToRemove); // Remove resource from task
                    } else {
                        System.out.println(" Resource not found.");
                    }
                    break;

                case 10:
                    return; // Return to main menu

                default:
                    System.out.println(" Invalid choice.");
                    break;
            }
        }
    }
    

    private static void manageGuests() {
        while (true) {
            System.out.println("\nGuest Management:");
            System.out.println("1. Add Guest");
            System.out.println("2. Remove Guest");
            System.out.println("3. Search Guest");
            System.out.println("4. Update Guest");
            System.out.println("5. Display Guest List");
            System.out.println("6. Display Sorted Guests");
            System.out.println("7. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = getValidInteger("Enter your choice: ");

            switch (choice) {
                case 1:
                    String guestCNIC = getValidCNIC("Enter Guest CNIC (13 digits): ");
                    String name = getValidString("Enter Name: ");
                    String contactNumber = getValidString("Enter Contact Number: ");
                    String email = getValidEmail("Enter Email: ");
                    guestManager.addGuest(new Guest(guestCNIC, name, contactNumber, email));
                    break;
                case 2:
                    String removeCNIC = getValidCNIC("Enter Guest CNIC to remove (13 digits): ");
                    guestManager.removeGuest(removeCNIC);
                    break;
                case 3:
                    String searchCNIC = getValidCNIC("Enter Guest CNIC to search (13 digits): ");
                    Guest guest = guestManager.searchGuest(searchCNIC);
                    System.out.println(guest != null ? guest : "Guest not found.");
                    break;
                case 4:
                    String updateCNIC = getValidCNIC("Enter Guest CNIC to update (13 digits): ");
                    String newName = getValidString("Enter new Name: ");
                    String newContact = getValidString("Enter new Contact Number: ");
                    String newEmail = getValidEmail("Enter new Email: ");
                    String newEventID = getValidString("Enter Event ID (or type 'None' if not applicable): ");
                    guestManager.updateGuest(updateCNIC, newName, newContact, newEmail, newEventID);
                    break;
                
                case 5:
                    guestManager.displayGuestList();
                    break;
                case 6:
                    guestManager.displaySortedGuests();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    } 

    
    public static void manageBudget() {
        BudgetManager budgetManager = new BudgetManager();
        while (true) {
            System.out.println("\n======= Budget Manager =======");
            System.out.println("1  Add Budget");
            System.out.println("2  Add Expense to Budget");
            System.out.println("3  View Remaining Budget");
            System.out.println("4  Update Budget Amount");
            System.out.println("5  Display All Budgets");
            System.out.println("6  Remove Budget");
            System.out.println("7  Exit");
            System.out.print(" Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(" Invalid choice. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter Budget Name: ");
                    String budgetName = scanner.nextLine().trim();
                    
                    System.out.print("Enter Total Budget Amount: ");
                    double totalAmount;
                    try {
                        totalAmount = Double.parseDouble(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid amount. Please enter a valid number.");
                        break;
                    }
                    
                    System.out.print("Enter Event ID for this budget: ");
                    String eventIdForBudget = scanner.nextLine().trim(); // Now storing as String
                    
                    budgetManager.addBudget(eventIdForBudget, budgetName, totalAmount);
                    break;
                case 2:
                    System.out.print(" Enter Budget Name: ");
                    String budgetToModify = scanner.nextLine().trim();
                    System.out.print(" Enter Expense Amount: ");
                    double expense = Double.parseDouble(scanner.nextLine().trim());
                    budgetManager.addExpenseToBudget(budgetToModify, expense);
                    break;
                case 3:
                    System.out.print(" Enter Budget Name: ");
                    String budgetToCheck = scanner.nextLine().trim();
                    System.out.println(" Remaining Budget: $" + budgetManager.getRemainingBudget(budgetToCheck));
                    break;
                case 4:
                    System.out.print(" Enter Budget Name: ");
                    String budgetToUpdate = scanner.nextLine().trim();
                    System.out.print(" Enter New Budget Amount: ");
                    double newAmount = Double.parseDouble(scanner.nextLine().trim());
                    budgetManager.updateBudgetAmount(budgetToUpdate, newAmount);
                    break;
                case 5:
                    budgetManager.displayBudgets();
                    break;
                case 6:
                    System.out.print(" Enter Budget Name to Remove: ");
                    String budgetToRemove = scanner.nextLine().trim();
                    budgetManager.removeBudget(budgetToRemove);
                    break;
                case 7:
                    return;
                default:
                    System.out.println(" Invalid choice. Please try again.");
            }
        }
    }

    private static void approveEvent() {
        String eventName = getValidString("Enter Event Name to Approve: ");
        Event event = eventManager.getEventById(eventName);
        planner.approveEvent(event);
    }

    private static void removeUser() {
        String clientName = getValidString("Enter Client Name to Remove: ");
        Client client = clientManager.getClientByCnic(clientName);
        planner.removeUser(client);
    }

    public static int getValidInteger(String prompt) {
        Scanner scanner = new Scanner(System.in);
        int result;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                result = scanner.nextInt();
                scanner.nextLine(); // consume the newline character
                break;
            } else {
                System.out.println(" Invalid input. Please enter a valid integer.");
                scanner.nextLine(); // consume invalid input
            }
        }
        return result;
    }
    

    private static double getValidDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static String getValidString(String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
        } while (input.isEmpty());
        return input;
    }

    private static boolean getValidBoolean(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("true") || input.equals("false")) {
                return Boolean.parseBoolean(input);
            }
            System.out.println("Invalid input. Please enter true or false.");
        }
    }

    private static String getValidCNIC(String prompt) {
        while (true) {
            System.out.print(prompt);
            String cnic = scanner.nextLine().trim();
            if (isValidCNIC(cnic)) {
                return cnic;
            } else {
                System.out.println("Invalid CNIC. Please enter a 13-digit number.");
            }
        }
    }

    private static String getValidEmail(String prompt) {
        while (true) {
            System.out.print(prompt);
            String email = scanner.nextLine().trim();
            if (isValidEmail(email)) {
                return email;
            } else {
                System.out.println("Invalid email. Please enter a valid email address.");
            }
        }
    }

    private static LocalDate getValidDate(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return LocalDate.parse(scanner.nextLine().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (Exception e) {
                System.out.println("Invalid date format. Please enter date in YYYY-MM-DD format.");
            }
        }
    }

    private static String getValidPriority(String prompt) {
        String priority;
        while (true) {
            priority = getValidString(prompt).toUpperCase();
            if (priority.equals("HIGH") || priority.equals("MEDIUM") || priority.equals("LOW")) {
                return priority;
            } else {
                System.out.println(" Invalid priority. Please enter High, Medium, or Low.");
            }
        }
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return email != null && pattern.matcher(email).matches();
    }

    public static boolean isValidCNIC(String cnic) {
        String cnicRegex = "^\\d{13}$";
        Pattern pattern = Pattern.compile(cnicRegex);
        return cnic != null && pattern.matcher(cnic).matches();
    }

    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        return password != null && pattern.matcher(password).matches();
    }
}












