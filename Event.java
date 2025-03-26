


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Event implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String eventID;
    private String name;
    private String date;
    private boolean isApproved;
    
    private List<Task> tasks;
    private List<Guest> guests;
    private List<Client> clients;
    private Budget budget; 

    public Event(String eventID, String name, String date, String parts) {
        this.eventID = eventID;
        this.name = name;
        this.date = date;
        this.isApproved = false;
        this.guests = new ArrayList<>();
        this.tasks = new ArrayList<>();
        this.clients = new ArrayList<>(); // Initialize list
        this.budget = null;
    }



    public String getEventID() {
        return eventID;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        this.isApproved = approved;
    }

    public void approveEvent(Event event) {
        event.setApproved(true);
        System.out.println("Event " + event.getName() + " has been approved by Admin.");
    }

    

    public List<Task> getTasks() {
        return new ArrayList<>(tasks); // Prevent external modification
    }

    public List<Client> getClients() {
        return new ArrayList<>(clients); // Prevent external modification
        }
        public List<Guest> getGuests() {
            return new ArrayList<>(guests);
        }   

        public Budget getBudget() {
            return budget;
        }

        // ************************ task ***************

        public Task findTaskById(int taskID) {
            for (Task task : tasks) {
                if (task.getId() == taskID) {
                    return task;
                }
            }
            return null;
        }

        public void addTask(Task task) {
            tasks.add(task);
        }
        
        public void addTaskById(int taskID) {
            Task task = findTaskById(taskID);
            if (task == null) {
                System.out.println("Task with ID " + taskID + " not found.");
                return;
            }
            // Only add the task if it's not already in the event
            if (!tasks.contains(task)) {
                tasks.add(task);
                task.setEvent(this);  // Maintain bidirectional link
                System.out.println(" Task " + taskID + " added to event " + name);
            } else {
                System.out.println(" Task with ID " + taskID + " already exists for this event.");
            }
        }
        
        public void removeTaskById(int taskID) {
            Task taskToRemove = findTaskById(taskID);
            if (taskToRemove != null) {
                tasks.remove(taskToRemove);
                taskToRemove.setEvent(null);  // Remove bidirectional link
                System.out.println(" Task " + taskID + " removed from event " + name);
            } else {
                System.out.println(" Task with ID " + taskID + " not found.");
            }
        }
        

        // ************************ guest ***************

        public Guest getGuestByCNIC(String guestCNIC) {
            for (Guest guest : guests) {
                if (guest.getGuestCNIC().equals(guestCNIC)) {
                    return guest;
                }
            }
            return null; 
        }


         
        public void addGuest(Guest guest) {
            if (guest == null) {
                System.out.println("Invalid guest.");
                return;
            }
            if (!guests.contains(guest)) {
                guests.add(guest);
                guest.setEventID(this.eventID);
              //  System.out.println("Guest " + guest.getName() + " added to event " + name);
            } else {
                System.out.println("Guest is already added to this event.");
            }
        }
        
        
        public void removeGuestById(String guestCNIC) {
            Guest guestToRemove = null;
            for (Guest g : guests) {
                if (g.getGuestCNIC().equals(guestCNIC)) {
                    guestToRemove = g;
                    break;
                }
            }
            if (guestToRemove != null) {
                guests.remove(guestToRemove);
                guestToRemove.setEventID(null);  // ✅ Remove event association
                System.out.println(" Guest " + guestToRemove.getName() + " removed from event " + name);
            } else {
                System.out.println(" Guest with CNIC " + guestCNIC + " not found.");
            }
        }
        // ************************ client ***************

        public void addClientByCnic(String clientCnic) {
            Client client = findClientByCnic(clientCnic);
            if (client != null) {
                clients.add(client);
                System.out.println(" Client " + client.getName() + " booked for event " + name);
            } else {
                System.out.println(" Client with CNIC " + clientCnic + " not found.");
            }
        }
        public void addClient(Client client) {
            if (!clients.contains(client)) {
                clients.add(client);
            } else {
                System.out.println(" Client " + client.getName() + " is already added to the event.");
            }
        }
        

        
        // Helper method to find the client by CNIC
        private Client findClientByCnic(String cnic) {
            return clients.stream()
                          .filter(client -> client.getCnic().equals(cnic))
                          .findFirst()
                          .orElse(null);
        }
        
        
        

        // ************************ budget ***************

                public void setBudget(Budget budget) {
            if (this.budget == null) { 
                this.budget = budget;
              //  System.out.println("Budget assigned to event " + name);
            } else {
                System.out.println("This event already has a budget.");
            }
        }
        
        public void removeBudget() {
            if (this.budget != null) {
                this.budget = null;
                System.out.println("Budget removed from event " + name);
            } else {
                System.out.println("No budget assigned to remove.");
            }
        }

        public Budget getBudgetByname(String budgetname) {
            if (this.budget != null && this.budget.getBudgetName().equals(budgetname)) {
                return this.budget;
            }
            return null; // Budget not found
        }
        




    
        public String toString() {
                        StringBuilder details = new StringBuilder();
                        details.append("\nEvent: ").append(name).append(" (Date: ").append(date).append(")\n");
                            //    .append("🔹 Approved: ").append(isApproved ? "Yes" : "No").append("\n");
                    
                        details.append("Tasks:\n");
                        for (Task task : tasks) {
                            details.append("   - ").append(task).append("\n");
                        }
                    
         

                        details.append("Guests:\n");
                        for (Guest guest : guests) {
                            details.append("   - ").append(guest).append("\n");
                        }
                    
            

                        details.append("Clients Booked:\n");
                        for (Client client : clients) {
                            details.append("   - ").append(client.getName()).append("\n");
                        }
                    

                        details.append("\nBudget Details:\n");
                        details.append(budget != null ? budget.toString() : "No budget assigned.\n");
                    
                        return details.toString();
                    }
                    
    
}
