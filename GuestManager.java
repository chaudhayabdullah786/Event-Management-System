import java.io.*;
import java.util.*;

class GuestManager {
    private LinkedList<Guest> guestList;
    private Guest root;
    private Queue<Guest> checkInQueue;
    private static final String FILE_NAME = "guests.txt";
    private static final String QUEUE_FILE = "checkin_queue.txt";

    public GuestManager() {
        guestList = new LinkedList<>();
        checkInQueue = new LinkedList<>();
        loadGuests();
        //loadCheckInQueue();
    }

    //  Add Guest
    public void addGuest(Guest guest) {
        if (guest == null) {
            System.out.println("Invalid guest.");
            return;
        }
        guestList.add(guest);
        root = insertGuest(root, guest);
        saveGuests();
        System.out.println("Guest " + guest.getName() + " added successfully.");
    }

    //  Remove Guest
    public void removeGuest(String guestCNIC) {
        Guest guest = searchGuest(guestCNIC);
        if (guest != null) {
            guestList.remove(guest);
            saveGuests();
            System.out.println("Guest with CNIC " + guestCNIC + " removed.");
        } else {
            System.out.println("Guest with CNIC " + guestCNIC + " not found.");
        }
    }

    //  Search Guest
    public Guest searchGuest(String guestCNIC) {
        return guestList.stream()
                .filter(guest -> guest.getGuestCNIC().equals(guestCNIC))
                .findFirst()
                .orElse(null);
    }

    // Update Guest
    public void updateGuest(String guestCNIC, String name, String contactNumber, String email, String eventID) {
        Guest guest = searchGuest(guestCNIC);
        if (guest != null) {
            guest.updateDetails(name, contactNumber, email);
            saveGuests();
            System.out.println("Guest details updated successfully.");
        } else {
            System.out.println("Guest not found.");
        }
    }

    //  Display All Guests
    public void displayGuestList() {
        if (guestList.isEmpty()) {
            System.out.println("No guests available.");
        } else {
            guestList.forEach(System.out::println);
        }
    }

    //  Insert Guest into Tree (for sorting)
    private Guest insertGuest(Guest root, Guest guest) {
        if (root == null) return guest;
        if (guest.getName().compareTo(root.getName()) < 0) {
            root.left = insertGuest(root.left, guest);
        } else {
            root.right = insertGuest(root.right, guest);
        }
        return root;
    }

    //  Display Guests in Sorted Order
    public void displaySortedGuests() {
        if (root == null) {
            System.out.println("No guests available.");
        } else {
            inorderTraversal(root);
        }
    }

    private void inorderTraversal(Guest root) {
        if (root != null) {
            inorderTraversal(root.left);
            System.out.println(root);
            inorderTraversal(root.right);
        }
    }

    

    private void saveGuests() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Guest guest : guestList) {
                writer.println(guest.getGuestCNIC() + "," + guest.getName() + "," + 
                               guest.getContactNumber() + "," + guest.getEmail() + "," + 
                               guest.getStatus() + "," + (guest.getEventID() != null ? guest.getEventID() : "None"));
            }
        } catch (IOException e) {
            System.out.println("Error saving guests: " + e.getMessage());
        }
    }

    // Load Guests from File
    private void loadGuests() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) {
                    Guest guest = new Guest(data[0].trim(), data[1], data[2], data[3]);
                    if (data.length == 6) {
                        guest.setEventID(data[5].trim());
                    }
                    guestList.add(guest);
                    root = insertGuest(root, guest);
                }
            }
        } catch (IOException e) {
            System.out.println("No previous guest data found.");
        }
    }

   
    public Guest getGuestByCNIC(String guestCNIC) {
        Guest guest = searchGuest(guestCNIC);
        if (guest == null) {
            System.out.println("Guest with CNIC " + guestCNIC + " not found.");
        }
        return guest;
    }

    
}
