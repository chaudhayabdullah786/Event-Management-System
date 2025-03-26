


import java.io.Serializable;

public class Guest implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String guestCNIC;
    private String name;
    private String contactNumber;
    private String email;
    private String status;
    Guest left, right;
    private String eventID; // Reference to associated Event

    public Guest(String guestCNIC, String name, String contactNumber, String email) {
        this.guestCNIC = guestCNIC;
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
        this.status = "Invited"; // Default status
        this.eventID = null; // Initially not assigned to any event
    }

    // Getters
    public String getGuestCNIC() {
        return guestCNIC;
    }

    public String getName() {
        return name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public String getEventID() {
        return eventID;
    }

    // Setter
    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    
    // Update Guest Details
    public void updateDetails(String name, String contactNumber, String email) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    // Guest Actions
    public void confirmAttendance() {
        this.status = "Confirmed";
    }

    public void checkIn() {
        this.status = "Checked-in";
    }

    public void checkOut() {
        this.status = "Checked-out";
    }

    // Display Guest Details
    @Override
    public String toString() {
        return "Guest CNIC: " + guestCNIC + 
               " | Name: " + name + 
               " | Contact: " + contactNumber + 
               " | Email: " + email + 
               " | Status: " + status + 
               " | Event ID: " + (eventID != null ? eventID : "None");
    }
    
}





