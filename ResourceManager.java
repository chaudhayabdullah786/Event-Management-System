import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class ResourceManager {
    private List<Resource> resources;
    private static final String FILE_NAME = "resources.txt";

    public ResourceManager() {
        this.resources = new ArrayList<>();
        this.loadResourcesFromFile();
    }

    public void addResource(Resource resource) {
        resources.add(resource);
        System.out.println("Resource added:\n" + resource);
        saveResourcesToFile();
    }
    
    // Fixed method to get Resource by ID, comparing integers instead of strings
    public Resource getResourceById(int resourceId) {
        for (Resource resource : resources) {
            if (resource.getId() == resourceId) {
                return resource;
            }
        }
        return null;
    }

 
    public void updateResource(int id, String name, boolean available) {
        Resource resource = getResourceById(id);
        if (resource != null) {
            resource.updateResource(name, available);
            System.out.println("Resource updated:\n" + resource);
            saveResourcesToFile();
        } else {
            System.out.println("Resource not found.");
        }
    }

    public void deleteResource(int id) {
        Resource resource = getResourceById(id);
        if (resource != null) {
            resources.remove(resource);
            System.out.println("Resource deleted.");
            saveResourcesToFile();
        } else {
            System.out.println("Resource not found.");
        }
    }

    public void displayResources() {
        if (resources.isEmpty()) {
            System.out.println("No resources available.");
        } else {
            for (Resource resource : resources) {
                System.out.println(resource);
                System.out.println("---------------------------");
            }
        }
    }

    public void sortResourcesByHeapSort() {
        buildHeap();
        for (int i = resources.size() - 1; i > 0; i--) {
            swap(0, i);
            heapify(0, i);
        }
        System.out.println("Resources sorted using Heap Sort.");
        saveResourcesToFile();
    }

    private void buildHeap() {
        for (int i = resources.size() / 2 - 1; i >= 0; i--) {
            heapify(i, resources.size());
        }
    }

    private void heapify(int i, int size) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < size && resources.get(left).getId() > resources.get(largest).getId()) {
            largest = left;
        }
        if (right < size && resources.get(right).getId() > resources.get(largest).getId()) {
            largest = right;
        }
        if (largest != i) {
            swap(i, largest);
            heapify(largest, size);
        }
    }

    private void swap(int i, int j) {
        Resource temp = resources.get(i);
        resources.set(i, resources.get(j));
        resources.set(j, temp);
    }

    public void saveResourcesToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Resource resource : resources) {
                writer.println(resource.toFileString());
            }
            System.out.println("Resources saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving resources: " + e.getMessage());
        }
    }

    public void loadResourcesFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No previous data found. Starting fresh.");
            return;
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Resource resource = Resource.fromFileString(line);
                if (resource != null) {
                    resources.add(resource);
                }
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("Error loading resources: " + e.getMessage());
        }
    }
}
