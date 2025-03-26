



import java.io.*;
import java.util.*;

public class EventManager {
    private static final String FILE_NAME = "events.txt";
    private LinkedList<Event> events;
    private List<Client> clients;
    private Stack<LinkedList<Event>> undoStack;
    private Stack<LinkedList<Event>> redoStack;
    private Queue<Event> eventQueue;
    
    public EventManager() {
       
        events = new LinkedList<>();
        clients = new ArrayList<>();
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        eventQueue = new LinkedList<>();
        loadEventsFromFile(); // Load saved events at startup
    }

    public void addEvent(Event event) {
        if (!eventExists(event)) {
            saveState();
            events.add(event);
            eventQueue.offer(event);
            sortEventsByDate();
            saveEventsToFile();
        } else {
            System.out.println("Event with ID " + event.getEventID() + " already exists!");
        }
    }

    public boolean eventExists(Event event) {
        return events.stream().anyMatch(e -> e.getEventID().equalsIgnoreCase(event.getEventID()));
    }

    public void deleteEvent(String eventID) {
        saveState();
        events.removeIf(event -> event.getEventID().equals(eventID));
        eventQueue.removeIf(event -> event.getEventID().equals(eventID));
        saveEventsToFile();
    }

    public Event getEventById(String eventID) {
        return events.stream()
                .filter(event -> event.getEventID().equals(eventID))
                .findFirst()
                .orElse(null);
    }

    public void updateEvent(Event updatedEvent) {
        saveState();
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getEventID().equals(updatedEvent.getEventID())) {
                events.set(i, updatedEvent);
                break;
            }
        }
        sortEventsByDate();
        saveEventsToFile();
    }

    private void saveState() {
        undoStack.push(new LinkedList<>(events));
        redoStack.clear();
    }

    // ************************** Client Management **************************

    public void addClientToEvent(String eventID, Client client) {
        Event event = getEventById(eventID);
        if (event != null) {
            event.addClient(client);  // Adding actual Client object
            saveEventsToFile();
        } else {
            System.out.println(" Event not found!");
        }
    }
    
    
    

    // ************************** Task Management **************************

    public void addTaskToEvent(String eventID, Task task) {
        Event event = getEventById(eventID);
        if (event != null) {
            event.addTask(task);  // Adding actual Task object
            saveEventsToFile();
        } else {
            System.out.println(" Event not found!");
        }
    }
    

    public void removeTaskFromEvent(String eventID, int taskID) {
        Event event = getEventById(eventID);
        if (event != null) {
            event.removeTaskById(taskID);
            saveEventsToFile();
        } else {
            System.out.println(" Event not found!");
        }
    }

    // ************************** Guest Management **************************

    public void addGuestToEvent(String eventID, Guest guest) {
        Event event = getEventById(eventID);
        if (event != null) {
            event.addGuest(guest);
            saveEventsToFile();
        } else {
            System.out.println(" Event not found!");
        }
    }

    public void removeGuestFromEvent(String eventID, String guestCNIC) {
        Event event = getEventById(eventID);
        if (event != null) {
            event.removeGuestById(guestCNIC);
            saveEventsToFile();
        } else {
            System.out.println("Event not found!");
        }
    }

    // ************************** Budget Management **************************

    
    public void assignBudgetToEvent(String eventID, String budgetName) {
        Event event = getEventById(eventID);
        BudgetManager budgetManager = new BudgetManager();  // Initialize the BudgetManager
    
        // Retrieve the budget by name from the BudgetManager
        Budget budget = budgetManager.getBudgetByName(budgetName);
    
        if (event != null && budget != null) {
            // Assign the budget to the event
            event.setBudget(budget);
            saveEventsToFile();  // Save changes to the file
         
        } else {
            if (event == null) {
                System.out.println("Event not found!");
            }
            if (budget == null) {
                System.out.println("Budget with name " + budgetName + " not found!");
            }
        }
    }
    

    public void removeBudgetFromEvent(String eventID) {
        Event event = getEventById(eventID);
        if (event != null) {
            event.removeBudget();
            saveEventsToFile();
        } else {
            System.out.println(" Event not found!");
        }
    }

    // ************************** Undo/Redo **************************

    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(new LinkedList<>(events));
            events = undoStack.pop();
            saveEventsToFile();
        } else {
            System.out.println(" Nothing to undo.");
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(new LinkedList<>(events));
            events = redoStack.pop();
            saveEventsToFile();
        } else {
            System.out.println(" Nothing to redo.");
        }
    }

    // ************************** Event Processing **************************

    public void processNextEvent() {
        Event nextEvent = eventQueue.poll();
        if (nextEvent != null) {
            System.out.println(" Processing event: " + nextEvent.getName() + " on " + nextEvent.getDate());
        } else {
            System.out.println(" No events to process.");
        }
    }
    public void sortEventsByDate() {
        events.sort(Comparator.comparing(Event::getDate));
    }

    public void displayAllEvents() {
        if (events.isEmpty()) {
            System.out.println("No events available.");
            return;
        }
    
        for (Event event : events) {
            System.out.println(event);  // Calls event.toString()
            System.out.println("--------------------------------------------------");
        }
    }

    // ************************** File Operations **************************



public void saveEventsToFile() {
    try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
        for (Event event : events) {
            // Save basic event details
            writer.print(event.getEventID() + "|" + event.getName() + "|" + event.getDate() + "|" + event.isApproved());

            // Save Tasks
            writer.print("|");
            for (Task task : event.getTasks()) {
                writer.print(task.getId() + ":" + task.getDescription() + ";");
            }

            // Save Clients
            writer.print("|");
            for (Client client : event.getClients()) {
                writer.print(client.getCnic() + ":" + client.getName() + ";");
            }

            // Save Guests
            writer.print("|");
            for (Guest guest : event.getGuests()) {
                writer.print(guest.getGuestCNIC() + ":" + guest.getName() + ":" + guest.getContactNumber() + ":" + guest.getEmail() + ";");
            }

            // Save Budget (if exists)
            writer.print("|");
            if (event.getBudget() != null) {
                writer.print(event.getBudget().getBudgetName() + ":" + event.getBudget().getTotalAmount() + ":" + event.getBudget().getUsedAmount());
            }

            writer.println();
        }
    } catch (IOException e) {
        System.err.println("Error saving events: " + e.getMessage());
    }
}


public void loadEventsFromFile() {
    try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split("\\|");
            if (parts.length >= 4) {
                Event event = new Event(parts[0], parts[1], parts[2], "");
                event.setApproved(Boolean.parseBoolean(parts[3]));

                // Load Tasks
                if (parts.length > 4 && !parts[4].isEmpty()) {
                    String[] taskDescriptions = parts[4].split(";");
                    for (String taskDesc : taskDescriptions) {
                        String[] taskParts = taskDesc.split(":");
                        if (taskParts.length == 2) {
                            try {
                                int taskID = Integer.parseInt(taskParts[0].trim());
                                String description = taskParts[1].trim();
                                Task task = new Task(taskID, description);
                                event.addTask(task);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid task ID format for: " + taskDesc);
                            }
                        }
                    }
                }

                // Load Clients
                if (parts.length > 5 && !parts[5].isEmpty()) {
                    String[] clientData = parts[5].split(";");
                    for (String clientInfo : clientData) {
                        String[] clientParts = clientInfo.split(":");
                        if (clientParts.length == 2) {
                            String cnic = clientParts[0].trim();
                            String name = clientParts[1].trim();
                            Client client = new Client(cnic, name, "Unknown"); // Adjust constructor as needed
                            event.addClient(client);
                            clients.add(client);
                        }
                    }
                }

                // Load Guests
                if (parts.length > 6 && !parts[6].isEmpty()) {
                    String[] guestData = parts[6].split(";");
                    for (String guestInfo : guestData) {
                        String[] guestParts = guestInfo.split(":");
                        if (guestParts.length == 4) {
                            String guestCNIC = guestParts[0].trim();
                            String guestName = guestParts[1].trim();
                            String contactNumber = guestParts[2].trim();
                            String email = guestParts[3].trim();
                            Guest guest = new Guest(guestCNIC, guestName, contactNumber, email);
                            event.addGuest(guest);  // ✅ Now adding guest properly to the event
                        }
                    }
                }


                // Load Budget
                if (parts.length > 7 && !parts[7].isEmpty()) {
                    String[] budgetParts = parts[7].split(":");
                    if (budgetParts.length == 3) {  // Expecting 3 parts: budgetName, totalAmount, and usedAmount
                        String budgetName = budgetParts[0].trim();
                        double totalAmount = Double.parseDouble(budgetParts[1].trim());
                        double usedAmount = Double.parseDouble(budgetParts[2].trim());
                        Budget budget = new Budget(event.getEventID(), budgetName, totalAmount, usedAmount);
                        event.setBudget(budget);
                    }
                }

                if (!eventExists(event)) {
                    events.add(event);
                    eventQueue.offer(event);
                }
            }
        }
        sortEventsByDate();
    } catch (IOException e) {
        System.err.println("Warning: Error loading events - " + e.getMessage());
    }
}



}
    
    
    

