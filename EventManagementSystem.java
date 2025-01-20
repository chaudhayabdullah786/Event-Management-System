import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class EventManagementSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final EventManager eventManager = new EventManager();
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createEvent();
                    break;
                case 2:
                    updateEvent();
                    break;
                case 3:
                    deleteEvent();
                    break;
                case 4:
                    registerAttendee();
                    break;
                case 5:
                    allocateResource();
                    break;
                case 6:
                    displayEventDetails();
                    break;
                case 7:
                    listAllEvents();
                    break;
                case 8:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        System.out.println("Thank you for using the Event Management System!");
    }

    private static void displayMenu() {
        System.out.println("\nEvent Management System");
        System.out.println("1. Create Event");
        System.out.println("2. Update Event");
        System.out.println("3. Delete Event");
        System.out.println("4. Register Attendee");
        System.out.println("5. Allocate Resource");
        System.out.println("6. Display Event Details");
        System.out.println("7. List All Events");
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void createEvent() {
        System.out.print("Enter event name: ");
        String name = scanner.nextLine();
        System.out.print("Enter event date and time (yyyy-MM-dd HH:mm): ");
        LocalDateTime dateTime = LocalDateTime.parse(scanner.nextLine(), dateTimeFormatter);
        System.out.print("Enter event location: ");
        String location = scanner.nextLine();
        System.out.print("Enter event description: ");
        String description = scanner.nextLine();

        Event event = eventManager.createEvent(name, dateTime, location, description);
        System.out.println("Event created successfully. Event ID: " + event.getId());
    }

    private static void updateEvent() {
        System.out.print("Enter event ID to update: ");
        int eventId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Event event = eventManager.getEvent(eventId);
        if (event == null) {
            System.out.println("Event not found.");
            return;
        }

        System.out.print("Enter new event name (press Enter to keep current): ");
        String name = scanner.nextLine();
        name = name.isEmpty() ? event.getName() : name;

        System.out.print("Enter new event date and time (yyyy-MM-dd HH:mm, press Enter to keep current): ");
        String dateTimeStr = scanner.nextLine();
        LocalDateTime dateTime = dateTimeStr.isEmpty() ? event.getDateTime() : LocalDateTime.parse(dateTimeStr, dateTimeFormatter);

        System.out.print("Enter new event location (press Enter to keep current): ");
        String location = scanner.nextLine();
        location = location.isEmpty() ? event.getLocation() : location;

        System.out.print("Enter new event description (press Enter to keep current): ");
        String description = scanner.nextLine();
        description = description.isEmpty() ? event.getDescription() : description;

        eventManager.updateEvent(eventId, name, dateTime, location, description);
        System.out.println("Event updated successfully.");
    }

    private static void deleteEvent() {
        System.out.print("Enter event ID to delete: ");
        int eventId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        eventManager.deleteEvent(eventId);
        System.out.println("Event deleted successfully.");
    }

    private static void registerAttendee() {
        System.out.print("Enter event ID: ");
        int eventId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter attendee name: ");
        String name = scanner.nextLine();
        System.out.print("Enter attendee contact info: ");
        String contactInfo = scanner.nextLine();

        Attendee attendee = eventManager.registerAttendee(eventId, name, contactInfo);
        if (attendee != null) {
            System.out.println("Attendee registered successfully. Attendee ID: " + attendee.getId());
        } else {
            System.out.println("Failed to register attendee. Event not found.");
        }
    }

    private static void allocateResource() {
        System.out.print("Enter event ID: ");
        int eventId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter resource name: ");
        String name = scanner.nextLine();
        System.out.print("Enter resource type: ");
        String type = scanner.nextLine();

        Resource resource = eventManager.allocateResource(eventId, name, type);
        if (resource != null) {
            System.out.println("Resource allocated successfully. Resource ID: " + resource.getId());
        } else {
            System.out.println("Failed to allocate resource. Event not found.");
        }
    }

    private static void displayEventDetails() {
        System.out.print("Enter event ID: ");
        int eventId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        eventManager.displayEventDetails(eventId);
    }

    private static void listAllEvents() {
        List<Event> events = eventManager.getAllEvents();
        if (events.isEmpty()) {
            System.out.println("No events found.");
        } else {
            System.out.println("All Events:");
            for (Event event : events) {
                System.out.println(event);
            }
        }
    }
}