import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class ShowtimeManager {

    public static void showShowtimes() {
        List<Showtime> showtimes = Showtime.fetchShowtimes();

        // Print table header
        System.out.println("\nShowtime List:");
        System.out.printf("%-5s %-10s %-10s %-20s %-10s\n",
                "ID", "MovieID", "TheaterID", "Start Time", "Seats");
        System.out.println("--------------------------------------------------------------------------------");

        // Print showtime data
        for (Showtime showtime : showtimes) {
            System.out.printf("%-5d %-10d %-10d %-20s %-10d\n",
                    showtime.getShowtimeID(),
                    showtime.getMovieID(),
                    showtime.getTheaterID(),
                    showtime.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                    showtime.getMaxSeats());
        }
    }

    public static void addShowtime(Scanner scanner) {
        System.out.println("\nAdd New Showtime:");
        System.out.print("Enter Movie ID: ");
        int movieID = scanner.nextInt();
        System.out.print("Enter Theater ID: ");
        int theaterID = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Start Time (yyyy-MM-dd HH:mm): ");
        String startTime = scanner.nextLine();
        System.out.print("Enter Max Seats: ");
        int maxSeats = scanner.nextInt();

        LocalDateTime dateTime = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        Showtime newShowtime = new Showtime(movieID, theaterID, dateTime, maxSeats);
        if (newShowtime.addShowtime()) {
            System.out.println("Showtime added successfully!");
        } else {
            System.out.println("Failed to add showtime.");
        }
    }

    public static void modifyShowtime(Scanner scanner) {
        showShowtimes();
        System.out.println("\nUpdate Showtime:");
        System.out.print("Enter the ID of the showtime to update: ");
        int showtimeID = scanner.nextInt();
        System.out.print("Enter New Movie ID: ");
        int movieID = scanner.nextInt();
        System.out.print("Enter New Theater ID: ");
        int theaterID = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter New Start Time (yyyy-MM-dd HH:mm): ");
        String startTime = scanner.nextLine();
        System.out.print("Enter New Max Seats: ");
        int maxSeats = scanner.nextInt();

        LocalDateTime dateTime = LocalDateTime.parse(startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        Showtime updatedShowtime = new Showtime(showtimeID, movieID, theaterID, dateTime, maxSeats);
        if (updatedShowtime.updateShowtime()) {
            System.out.println("Showtime updated successfully!");
        } else {
            System.out.println("Failed to update showtime.");
        }
    }

    public static void deleteShowtime(Scanner scanner) {
        showShowtimes();
        System.out.println("\nDelete Showtime:");
        System.out.print("Enter the ID of the showtime to delete: ");
        int showtimeID = scanner.nextInt();

        if (Showtime.deleteShowtime(showtimeID)) {
            System.out.println("Showtime deleted successfully!");
        } else {
            System.out.println("Failed to delete showtime.");
        }
    }
}

