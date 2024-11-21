import java.util.Scanner;
public class Menu {

    // Admin Menu
    public static void showAdminMenu(Scanner scanner) {
        System.out.println("\nAdmin Menu:");
        System.out.println("1. View Theaters");
        System.out.println("2. Add Theater");
        System.out.println("3. Modify Theater");
        System.out.println("4. Delete Theater");
        System.out.println("5. View Movies");
        System.out.println("6. Add Movie");
        System.out.println("7. Modify Movie");
        System.out.println("8. Delete Movie");
        System.out.println("9. View Showtimes");
        System.out.println("10. Add Showtime");
        System.out.println("11. Modify Showtime");
        System.out.println("12. Delete Showtime");
        System.out.println("13. Exit");
        System.out.print("Please select an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                TheaterManager.showTheaters();
                break;
            case 2:
                TheaterManager.addTheater(scanner);
                break;
            case 3:
                TheaterManager.modifyTheater(scanner);
                break;
            case 4:
                TheaterManager.deleteTheater(scanner);
                break;
            case 5:
                MovieManager.showMovies();
                break;
            case 6:
                MovieManager.addMovie(scanner);
                break;
            case 7:
                MovieManager.modifyMovie(scanner);
                break;
            case 8:
                MovieManager.deleteMovie(scanner);
                break;
            case 9:
                ShowtimeManager.showShowtimes();
                break;
            case 10:
                ShowtimeManager.addShowtime(scanner);
                break;
            case 11:
                ShowtimeManager.modifyShowtime(scanner);
                break;
            case 12:
                ShowtimeManager.deleteShowtime(scanner);
                break;
            case 13:
                System.out.println("Ending Admin");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Try again.");
        }
    }

    public static void showGuestMenu(Scanner scanner) {
        System.out.println("\nGuest Menu:");
        System.out.println("1. View Theaters");
        System.out.println("2. Browse Movies");
        System.out.println("3. View Showtimes");
        System.out.println("4. Select Theater");
        System.out.println("5. Select Movie");
        System.out.println("6. Purchase tickets");
        System.out.println("7. Exit");
        System.out.print("Please select an option: ");
    }



    public static void showLogin(Scanner scanner) {
        System.out.println("\nNeed to implement");
    }


    public static void showSign(Scanner scanner) {
        System.out.println("\nNeed to implement");
    }
}
