import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Task implements Comparable<Task> {
    private static int idCounter = 1;
    private int id;
    private String description;
    private String priority;
    private LocalDate dueDate;
    private String assignee;
    private boolean completed;
    public List<Resource> resources; // Many-to-Many Relationship with Resource
    private Event event; 

    // Constructor
    public Task(String description, String priority, LocalDate dueDate, String assignee) {
        try {
            this.id = generateId();
            setDescription(description);
            setPriority(priority);
            setDueDate(dueDate);
            setAssignee(assignee);
            this.completed = false;
            this.resources = new ArrayList<>(); //  Initialize List
        } catch (IllegalArgumentException e) {
            System.err.println(" Task creation failed: " + e.getMessage());
            throw e;
        }
    }

    // Fix for the second constructor
    public Task(int taskId, String taskDesc) {
        this.id = taskId;
        this.description = taskDesc;
        this.resources = new ArrayList<>(); // Initialize List
    }

    private static int generateId() { // Ensure unique IDs
        return idCounter++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getPriority() {
        return priority;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getAssignee() {
        return assignee;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<Resource> getResources() {
        return new ArrayList<>(resources); // Return a copy to prevent external modification
    }

    public void setPriority(String priority) {
        if (priority == null || priority.trim().isEmpty() || !isValidPriority(priority)) {
            throw new IllegalArgumentException("Invalid priority: " + priority + ". Use High, Medium, or Low.");
        }
        this.priority = priority;
    }

    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
        this.description = description;
    }

    public void setDueDate(LocalDate dueDate) {
        if (dueDate == null || dueDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Due date must be today or in the future.");
        }
        this.dueDate = dueDate;
    }

    public void setAssignee(String assignee) {
        if (assignee == null || assignee.trim().isEmpty()) {
            throw new IllegalArgumentException("Assignee name cannot be empty.");
        }
        this.assignee = assignee;
    }

    public boolean markCompleted() {
        if (!this.completed) {
            this.completed = true;
            return true; // Task successfully marked as completed
        }
        return false; // Task was already completed
    }

    // Add a resource to the task (Bidirectional Link)
    public void addResource(Resource resource) {
        if (resource == null) {
            throw new IllegalArgumentException("Resource cannot be null.");
        }
        if (!resources.contains(resource)) {
            resources.add(resource);
            resource.assignTask(this); // Ensure bidirectional association with Resource
        }
    }

    // Remove a resource from the task (Bidirectional Removal)
    public void removeResource(Resource resource) {
        if (resource == null) {
            throw new IllegalArgumentException("Resource cannot be null.");
        }
        if (resources.remove(resource)) {
            resource.removeTask(this); // Ensure bidirectional removal from Resource
        }
    }

    @Override
    public int compareTo(Task other) {
        if (other == null || other.dueDate == null || this.dueDate == null) {
            throw new NullPointerException("Task comparison failed due to null due date.");
        }
        int priorityComparison = Integer.compare(getPriorityLevel(this.priority), getPriorityLevel(other.priority));
        return (priorityComparison != 0) ? priorityComparison : this.dueDate.compareTo(other.dueDate);
    }

    private int getPriorityLevel(String priority) {
        switch (priority.toLowerCase()) {
            case "high": return 1;
            case "medium": return 2;
            case "low": return 3;
            default: return Integer.MAX_VALUE;
        }
    }

    private boolean isValidPriority(String priority) {
        return priority.equalsIgnoreCase("High") || priority.equalsIgnoreCase("Medium") || priority.equalsIgnoreCase("Low");
    }

    @Override
    public String toString() {
        return "Task [ID=" + id + ", Description=" + description + ", Priority=" + priority +
               ", DueDate=" + dueDate + ", Assignee=" + assignee + ", Completed=" + completed +
               ", Resource Count=" + resources.size() + "]";
    }
    
    


}
