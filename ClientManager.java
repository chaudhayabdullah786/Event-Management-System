import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

class ClientManager {
    private LinkedList<Client> clients;
    private static final String FILE_NAME = "clients.txt";

    public ClientManager() {
        this.clients = new LinkedList<>();
        loadClientsFromFile();
    }

    public void addClient(String cnic, String name, String email) {
        // Assuming Client id is auto-generated in the constructor
        Client client = new Client(cnic, name, email);
        clients.add(client);
        saveClientsToFile();
    }

    public Client getClientByCnic(String cnic) {
        System.out.println("Searching for CNIC: " + cnic); // Debug
        for (Client client : clients) {
            System.out.println("Checking CNIC: " + client.getCnic()); // Debug
            if (client.getCnic().trim().equals(cnic.trim())) {
                return client;
            }
        }
        return null;
    }

    public void updateClient(String cnic, String name, String email) {
        Client client = getClientByCnic(cnic);
        if (client != null) {
            client.updateClient(name, email);
            saveClientsToFile();
        } else {
            System.out.println("Client not found.");
        }
    }

    public void deleteClient(String cnic) {
        clients.removeIf(client -> client.getCnic().equals(cnic));
        saveClientsToFile();
    }

    public void displayClients() {
        if (clients.isEmpty()) {
            System.out.println("No clients found.");
        } else {
            for (Client client : clients) {
                System.out.println(client);
            }
        }
    }

    public void sortClientsById() {
        LinkedList<Client> sortedList = new LinkedList<>();
        for (Client client : clients) {
            if (sortedList.isEmpty()) {
                sortedList.add(client);
            } else {
                int i = 0;
                while (i < sortedList.size() && sortedList.get(i).getId() < client.getId()) {
                    i++;
                }
                sortedList.add(i, client);
            }
        }
        clients = sortedList;
        saveClientsToFile();
    }

    private void saveClientsToFile() {
        File tempFile = new File("clients_temp.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            for (Client client : clients) {
                writer.write(client.getId() + "," + client.getCnic() + "," + client.getName() + "," + client.getEmail());
                writer.newLine();
            }
            writer.close();
            
            File originalFile = new File(FILE_NAME);
            if (originalFile.exists()) originalFile.delete();
            tempFile.renameTo(originalFile);
        } catch (IOException e) {
            System.err.println("Error saving clients to file: " + e.getMessage());
        }
    }
    

    private void loadClientsFromFile() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                return;
            }
    
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] data = line.split(",");
                    if (data.length == 4) { // Ensure all values exist
                        int id = Integer.parseInt(data[0]); // Parse ID
                        String cnic = data[1].trim();
                        String name = data[2].trim();
                        String email = data[3].trim();
                        
                        clients.add(new Client(id, cnic, name, email));
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading clients from file: " + e.getMessage());
        }
    }
    
}
