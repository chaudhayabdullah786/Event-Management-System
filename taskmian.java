

// import java.time.LocalDate;
// import java.time.format.DateTimeParseException;
// import java.util.Scanner;

// public class taskmian {
//     public static void main(String[] args) {
//         Scanner scanner = new Scanner(System.in);
//         TaskManager taskManager = new TaskManager();
//         int choice;

//         do {
//             System.out.println("\n===== Task Management System =====");
//             System.out.println("1. Add Task");
//             System.out.println("2. Remove Task");
//             System.out.println("3. Search Task");
//             System.out.println("4. Mark Task as Completed");
//             System.out.println("5. Process Next Task");
//             System.out.println("6. Display All Tasks");
//             System.out.println("7. Update Task");
//             System.out.println("0. Exit");
//             System.out.print("Enter your choice: ");

//             // Validate menu choice
//             while (!scanner.hasNextInt()) {
//                 System.out.println(" Error: Please enter a valid number.");
//                 scanner.next(); // Discard invalid input
//                 System.out.print("Enter your choice: ");
//             }
//             choice = scanner.nextInt();
//             scanner.nextLine(); // Consume newline

//             switch (choice) {
//                 case 1:
//                     // Add Task with Validation
//                     String description = getValidDescription(scanner);
//                     String priority = getValidPriority(scanner);
//                     LocalDate dueDate = getValidDueDate(scanner);
//                     String assignee = getValidAssignee(scanner);

//                     Task newTask = new Task(description, priority, dueDate, assignee);
//                     taskManager.addTask(newTask);
//                     System.out.println(" Task added successfully!");
//                     break;

//                 case 2:
//                     // Remove Task
//                     int removeId = getValidTaskId(scanner, "Enter Task ID to remove: ");
//                     taskManager.removeTask(removeId);
//                     System.out.println(" Task removed successfully!");
//                     break;

//                 case 3:
//                     // Search Task
//                     int searchId = getValidTaskId(scanner, "Enter Task ID to search: ");
//                     Task foundTask = taskManager.searchTask(searchId);
//                     if (foundTask != null) {
//                         System.out.println("🔍 Task Found: " + foundTask);
//                     } else {
//                         System.out.println(" Task not found!");
//                     }
//                     break;

//                 case 4:
//                     // Mark Task as Completed
//                     int completeId = getValidTaskId(scanner, "Enter Task ID to mark as completed: ");
//                     taskManager.markTaskCompleted(completeId);
//                     System.out.println(" Task marked as completed!");
//                     break;

//                 case 5:
//                     // Process Next Task
//                     taskManager.processNextTask();
//                     break;

//                 case 6:
//                     // Display All Tasks
//                     taskManager.displayTasks();
//                     break;

//                 case 7:
//                     // Update Task
//                     int updateId = getValidTaskId(scanner, "Enter Task ID to update: ");
//                     String newDescription = getValidDescription(scanner);
//                     String newPriority = getValidPriority(scanner);
//                     LocalDate newDueDate = getValidDueDate(scanner);
//                     String newAssignee = getValidAssignee(scanner);

//                     boolean updated = taskManager.updateTask(updateId, newDescription, newPriority, newDueDate, newAssignee);
//                     if (updated) {
//                         System.out.println(" Task updated successfully!");
//                     } else {
//                         System.out.println(" Task update failed!");
//                     }
//                     break;

//                 case 0:
//                     System.out.println(" Exiting Task Management System. Goodbye!");
//                     break;

//                 default:
//                     System.out.println("S Invalid choice. Please try again.");
//                     break;
//             }
//         } while (choice != 0);

//         scanner.close();
//     }

//     // =================== Helper Methods for Input Validation ===================

//     // Validate Task Description
//     private static String getValidDescription(Scanner scanner) {
//         while (true) {
//             System.out.print("Enter Task Description: ");
//             String input = scanner.nextLine().trim();
//             if (!input.isEmpty()) {
//                 return input;
//             }
//             System.out.println(" Error: Task description cannot be empty.");
//         }
//     }

//     // Validate Task Priority
//     private static String getValidPriority(Scanner scanner) {
//         while (true) {
//             System.out.print("Enter Task Priority (High/Medium/Low): ");
//             String input = scanner.nextLine().trim();
//             if (input.equalsIgnoreCase("High") || input.equalsIgnoreCase("Medium") || input.equalsIgnoreCase("Low")) {
//                 return input;
//             }
//             System.out.println(" Error: Invalid priority! Enter 'High', 'Medium', or 'Low'.");
//         }
//     }

//     // Validate Due Date
//     private static LocalDate getValidDueDate(Scanner scanner) {
//         while (true) {
//             System.out.print("Enter Due Date (YYYY-MM-DD): ");
//             String input = scanner.nextLine().trim();
//             try {
//                 LocalDate date = LocalDate.parse(input);
//                 if (!date.isBefore(LocalDate.now())) {
//                     return date;
//                 }
//                 System.out.println(" Error: Due date must be today or in the future.");
//             } catch (DateTimeParseException e) {
//                 System.out.println(" Error: Invalid date format. Use YYYY-MM-DD.");
//             }
//         }
//     }

//     // Validate Assignee Name
//     private static String getValidAssignee(Scanner scanner) {
//         while (true) {
//             System.out.print("Enter Assignee Name: ");
//             String input = scanner.nextLine().trim();
//             if (!input.isEmpty()) {
//                 return input;
//             }
//             System.out.println(" Error: Assignee name cannot be empty.");
//         }
//     }

//     // Validate Task ID Input
//     private static int getValidTaskId(Scanner scanner, String prompt) {
//         while (true) {
//             System.out.print(prompt);
//             if (scanner.hasNextInt()) {
//                 int id = scanner.nextInt();
//                 scanner.nextLine(); // Consume newline
//                 return id;
//             } else {
//                 System.out.println(" Error: Task ID must be a valid number.");
//                 scanner.next(); // Discard invalid input
//             }
//         }
//     }
// }
