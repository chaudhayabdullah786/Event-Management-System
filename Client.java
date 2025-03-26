import java.util.ArrayList;
import java.util.List;

class Client {
    private static int idCounter = 1; // Auto-increment ID counter
    private int id;
    private String cnic;
    private String name;
    private String email;
    private List<Event> bookedEvents;

    // Constructor for new clients (auto-generates ID)
    public Client(String cnic, String name, String email) {
        this.id = idCounter++;
        this.cnic = cnic;
        this.name = name;
        this.email = email;
        this.bookedEvents = new ArrayList<>();
    }

    // Constructor for loading clients from file (preserves existing ID)
    public Client(int id, String cnic, String name, String email) {
        this.id = id;
        this.cnic = cnic;
        this.name = name;
        this.email = email;
        this.bookedEvents = new ArrayList<>();
        idCounter = Math.max(idCounter, id + 1); // Ensures ID continues from the highest value
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getCnic() {
        return cnic;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    // Update client details
    public void updateClient(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Convert client data to a string for storing in the file
    @Override
    public String toString() {
        return id + "," + cnic + "," + name + "," + email;
    }
}
