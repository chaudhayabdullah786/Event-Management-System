import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event {
    private int id;
    private String name;
    private LocalDateTime dateTime;
    private String location;
    private String description;
    private List<Attendee> attendees;
    private List<Resource> resources;

    public Event(int id, String name, LocalDateTime dateTime, String location, String description) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        this.location = location;
        this.description = description;
        this.attendees = new ArrayList<>();
        this.resources = new ArrayList<>();
    }

    // Getters and setters
    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<Attendee> getAttendees() { return attendees; }
    public List<Resource> getResources() { return resources; }

    public void addAttendee(Attendee attendee) {
        attendees.add(attendee);
    }

    public void removeAttendee(Attendee attendee) {
        attendees.remove(attendee);
    }

    public void allocateResource(Resource resource) {
        resources.add(resource);
    }

    public void deallocateResource(Resource resource) {
        resources.remove(resource);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateTime=" + dateTime +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", attendees=" + attendees.size() +
                ", resources=" + resources.size() +
                '}';
    }
}