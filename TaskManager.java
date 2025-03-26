import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

class TaskManager {
    private Queue<Task> tasks;
    private static final String FILE_NAME = "tasks.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public TaskManager() {
        this.tasks = new PriorityQueue<>();
        loadTasksFromFile();
    }

    public void addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null.");
        }
        tasks.offer(task);
        saveTasksToFile();
    }

    public void removeTask(int taskId) {
        Task taskToRemove = searchTask(taskId);
        if (taskToRemove != null) {
            // Ensure bidirectional removal from all assigned resources
            for (Resource resource : new ArrayList<>(taskToRemove.getResources())) {
                resource.removeTask(taskToRemove);
            }
            tasks.remove(taskToRemove);
            saveTasksToFile();
            System.out.println(" Task removed successfully.");
        } else {
            System.out.println(" Task ID not found.");
        }
    }

    public Task searchTask(int taskId) {
        for (Task task : tasks) {
            if (task.getId() == taskId) {
                return task;
            }
        }
        return null;
    }

    public void markTaskCompleted(int taskId) {
        Task task = searchTask(taskId);
        if (task != null && task.markCompleted()) {
            saveTasksToFile();
            System.out.println(" Task marked as completed.");
        } else {
            System.out.println(" Task not found or already completed.");
        }
    }

    public void processNextTask() {
        if (!tasks.isEmpty()) {
            Task task = tasks.poll();
            task.markCompleted();
            System.out.println(" Processed Task: " + task);
            saveTasksToFile();
        } else {
            System.out.println(" No tasks to process.");
        }
    }

    public void displayTasks() {
        if (tasks.isEmpty()) {
            System.out.println(" No tasks available.");
        } else {
            List<Task> sortedTasks = new ArrayList<>(tasks);
            Collections.sort(sortedTasks);
            for (Task task : sortedTasks) {
                System.out.println(task);
            }
        }
    }

    public boolean updateTask(int id, String newDescription, String newPriority, LocalDate newDueDate, String newAssignee) {
        Task task = searchTask(id);
        if (task != null) {
            task.setDescription(newDescription);
            task.setPriority(newPriority);
            task.setDueDate(newDueDate);
            task.setAssignee(newAssignee);
            saveTasksToFile();
            return true;
        }
        return false;
    }

    public void addResourceToTask(int taskId, Resource resource) {
        Task task = searchTask(taskId);
    
        if (task != null && resource != null) {
            // Avoid adding the same resource twice
            if (!task.getResources().contains(resource)) {
                task.addResource(resource);
                resource.assignTask(task);
                saveTasksToFile();
                System.out.println(" Resource " + resource.getName() + " added to Task " + taskId);
            } else {
                System.out.println(" Resource already assigned to this task.");
            }
        } else {
            System.out.println(" Task or Resource not found.");
        }
    }
    
    public void removeResourceFromTask(int taskId, Resource resourceToRemove) {
        Task task = searchTask(taskId);
    
        if (task != null && resourceToRemove != null) {
            if (task.getResources().contains(resourceToRemove)) {
                task.removeResource(resourceToRemove);
                resourceToRemove.removeTask(task);
                saveTasksToFile();
                System.out.println(" Resource " + resourceToRemove.getName() + " removed from Task " + taskId);
            } else {
                System.out.println(" Resource not assigned to this task.");
            }
        } else {
            System.out.println(" Task or Resource not found.");
        }
    }
    

    private void saveTasksToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                StringBuilder resourcesString = new StringBuilder();
                for (Resource resource : task.getResources()) {
                    resourcesString.append(resource.getName()).append(";");
                }
                writer.println(task.getId() + "," + task.getDescription() + "," + task.getPriority() + "," +
                        task.getDueDate() + "," + task.getAssignee() + "," + task.isCompleted() + "," + resourcesString);
            }
        } catch (IOException e) {
            System.err.println("❌ Error saving tasks: " + e.getMessage());
        }
    }

    private void loadTasksFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    Task task = new Task(parts[1], parts[2], LocalDate.parse(parts[3], FORMATTER), parts[4]);
                    task.setId(Integer.parseInt(parts[0]));
                    if (Boolean.parseBoolean(parts[5])) {
                        task.markCompleted();
                    }

                    // Assign resources if available
                    if (parts.length > 6) {
                        String[] resources = parts[6].split(";");
                        for (String resourceName : resources) {
                            if (!resourceName.isEmpty()) {
                                Resource newResource = new Resource(0, resourceName, false);
                                task.addResource(newResource);
                                newResource.assignTask(task); // Ensure bidirectional linking
                            }
                        }
                    }

                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.err.println(" Warning: Error loading tasks - " + e.getMessage());
        }
    }

    public Task getTaskById(int taskId) {
        for (Task task : tasks) { // Assuming you have a list of tasks
            if (task.getId() == taskId) { // Check if the task ID matches
                return task;
            }
        }
        return null; // Return null if no matching task is found
    }

    
}