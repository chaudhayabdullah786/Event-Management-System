import java.util.ArrayList;
import java.util.List;

public class Resource {
    private int id;
    private String name;
    private boolean available;
    private List<Task> tasks; // Many-to-Many Relationship

    // Constructor with ID
    public Resource(int id, String name, boolean available) {
        this.id = id;
        this.name = name;
        this.available = available;
        this.tasks = new ArrayList<>(); //  Initialize List
    }
    
    // Constructor for loading from file (ensure initialization)
    public Resource(int id) {
        this.id = id;
        this.name = "";
        this.available = true;
        this.tasks = new ArrayList<>(); //  Initialize List
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getName() { return name; }
    public boolean isAvailable() { return available; }
    
    public List<Task> getTasks() { 
        return new ArrayList<>(tasks); // Return a copy to prevent external modification 
    }

    public void setAvailable(boolean available) { this.available = available; }

    // Assign a task to the resource (Bidirectional Link)
    public void assignTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null.");
        }
        if (!tasks.contains(task)) {
            tasks.add(task);
            if (task.getResources() == null) { 
                task.resources = new ArrayList<>(); 
            }
            task.addResource(this); // Ensure bidirectional link
        }
    }
    

    // Remove a task from the resource (Bidirectional Removal)
    public void removeTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null.");
        }
        if (tasks.remove(task)) {
            task.removeResource(this); // Ensure bidirectional removal
        }
    }

    // Update resource details
    public void updateResource(String name, boolean available) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Resource name cannot be empty.");
        }
        this.name = name;
        this.available = available;
    }

    // Delete resource (Reset Fields)
    public void deleteResource() {
        for (Task task : new ArrayList<>(tasks)) { // Avoid ConcurrentModificationException
            task.removeResource(this);
        }
        this.name = null;
        this.available = false;
        this.tasks.clear();
    }

    public String toString() {
        
        
        return "Resource [ID=" + id + ", Name=" + name + ", Available=" + available ;
    }

    // Converts the resource data to a string format suitable for file storage.
    public String toFileString() {
        StringBuilder taskIds = new StringBuilder();
        for (Task task : tasks) {
            taskIds.append(task.getId()).append(",");
        }
        return id + "," + name + "," + available + "," + taskIds.toString();
    }

    // Parses a Resource from a file string.
    public static Resource fromFileString(String line) {
        String[] parts = line.split(",");
        if (parts.length < 3) return null; // Basic validation
    
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        boolean available = Boolean.parseBoolean(parts[2]);

        Resource resource = new Resource(id, name, available);
        // Load associated tasks later in TaskManager
        return resource;
    }
}
