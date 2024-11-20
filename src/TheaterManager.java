import java.util.List;
import java.util.Scanner;

public class TheaterManager {

    public static void showTheaters() {
        List<Theater> theaters = Theater.fetchTheaters();

        System.out.println("\nTheater List:");
        System.out.printf("%-5s %-30s %-50s\n", "ID", "Name", "Location");
        System.out.println("-------------------------------------------------------------------------------------------");

        for (Theater theater : theaters) {
            System.out.printf("%-5d %-30s %-50s\n",
                    theater.getTheaterID(),
                    theater.getName(),
                    theater.getLocation());
        }
    }

    public static void addTheater(Scanner scanner) {
        System.out.print("Enter theater name: ");
        String name = scanner.nextLine();
        System.out.print("Enter theater location: ");
        String location = scanner.nextLine();

        Theater newTheater = new Theater(name, location);
        if (newTheater.addTheater()) {
            System.out.println("Theater added successfully!");
        } else {
            System.out.println("Failed to add theater.");
        }
    }

    public static void modifyTheater(Scanner scanner) {
        showTheaters();
        System.out.print("Enter the ID of the theater to modify: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new theater name: ");
        String newName = scanner.nextLine();
        System.out.print("Enter new theater location: ");
        String newLocation = scanner.nextLine();

        Theater updatedTheater = new Theater(id, newName, newLocation);
        if (updatedTheater.updateTheater()) {
            System.out.println("Theater updated successfully!");
        } else {
            System.out.println("Failed to update theater.");
        }
    }

    public static void deleteTheater(Scanner scanner) {
        showTheaters();
        System.out.print("Enter the ID of the theater to delete: ");
        int id = scanner.nextInt();

        if (Theater.deleteTheater(id)) {
            System.out.println("Theater deleted successfully!");
        } else {
            System.out.println("Failed to delete theater.");
        }
    }
}
