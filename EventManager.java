import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {
    private Map<Integer, Event> events;
    private int eventIdCounter;
    private int attendeeIdCounter;
    private int resourceIdCounter;

    public EventManager() {
        this.events = new HashMap<>();
        this.eventIdCounter = 1;
        this.attendeeIdCounter = 1;
        this.resourceIdCounter = 1;
    }

    public Event createEvent(String name, LocalDateTime dateTime, String location, String description) {
        Event event = new Event(eventIdCounter++, name, dateTime, location, description);
        events.put(event.getId(), event);
        return event;
    }

    public void updateEvent(int eventId, String name, LocalDateTime dateTime, String location, String description) {
        Event event = events.get(eventId);
        if (event != null) {
            event.setName(name);
            event.setDateTime(dateTime);
            event.setLocation(location);
            event.setDescription(description);
        }
    }

    public void deleteEvent(int eventId) {
        events.remove(eventId);
    }

    public Event getEvent(int eventId) {
        return events.get(eventId);
    }

    public List<Event> getAllEvents() {
        return new ArrayList<>(events.values());
    }

    public Attendee registerAttendee(int eventId, String name, String contactInfo) {
        Event event = events.get(eventId);
        if (event != null) {
            Attendee attendee = new Attendee(attendeeIdCounter++, name, contactInfo);
            event.addAttendee(attendee);
            return attendee;
        }
        return null;
    }

    public void unregisterAttendee(int eventId, int attendeeId) {
        Event event = events.get(eventId);
        if (event != null) {
            event.getAttendees().removeIf(attendee -> attendee.getId() == attendeeId);
        }
    }

    public Resource allocateResource(int eventId, String name, String type) {
        Event event = events.get(eventId);
        if (event != null) {
            Resource resource = new Resource(resourceIdCounter++, name, type);
            event.allocateResource(resource);
            return resource;
        }
        return null;
    }

    public void deallocateResource(int eventId, int resourceId) {
        Event event = events.get(eventId);
        if (event != null) {
            event.getResources().removeIf(resource -> resource.getId() == resourceId);
        }
    }

    public void displayEventDetails(int eventId) {
        Event event = events.get(eventId);
        if (event != null) {
            System.out.println("Event Details:");
            System.out.println(event);
            System.out.println("Attendees:");
            for (Attendee attendee : event.getAttendees()) {
                System.out.println(attendee);
            }
            System.out.println("Resources:");
            for (Resource resource : event.getResources()) {
                System.out.println(resource);
            }
        } else {
            System.out.println("Event not found.");
        }
    }
}