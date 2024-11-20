import java.util.List;
import java.util.Scanner;

public class MovieManager {

    public static void showMovies() {
        List<Movie> movies = Movie.fetchMovies();

        System.out.println("\nMovie List:");
        System.out.printf("%-5s %-30s %-15s %-10s %-50s %-10s\n",
                "ID", "Title", "Genre", "Rating", "Synopsis", "Length");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------");

        for (Movie movie : movies) {
            System.out.println(movie.toTableString());
        }
    }

    public static void addMovie(Scanner scanner) {
        System.out.println("\nAdd New Movie:");
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();
        System.out.print("Enter rating: ");
        String rating = scanner.nextLine();
        System.out.print("Enter synopsis: ");
        String synopsis = scanner.nextLine();
        System.out.print("Enter length (e.g., 2h 30min): ");
        String length = scanner.nextLine();

        Movie newMovie = new Movie(title, genre, rating, synopsis, length);
        if (newMovie.addMovie()) {
            System.out.println("Movie added successfully!");
        } else {
            System.out.println("Failed to add movie.");
        }
    }

    public static void modifyMovie(Scanner scanner) {
        showMovies();
        System.out.println("\nUpdate Movie:");
        System.out.print("Enter the ID of the movie to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new title: ");
        String title = scanner.nextLine();
        System.out.print("Enter new genre: ");
        String genre = scanner.nextLine();
        System.out.print("Enter new rating: ");
        String rating = scanner.nextLine();
        System.out.print("Enter new synopsis: ");
        String synopsis = scanner.nextLine();
        System.out.print("Enter new length (e.g., 2h 30min): ");
        String length = scanner.nextLine();

        Movie updatedMovie = new Movie(id, title, genre, rating, synopsis, length);
        if (updatedMovie.updateMovie()) {
            System.out.println("Movie updated successfully!");
        } else {
            System.out.println("Failed to update movie.");
        }
    }

    public static void deleteMovie(Scanner scanner) {
        showMovies();
        System.out.println("\nDelete Movie:");
        System.out.print("Enter the ID of the movie to delete: ");
        int id = scanner.nextInt();

        if (Movie.deleteMovie(id)) {
            System.out.println("Movie deleted successfully!");
        } else {
            System.out.println("Failed to delete movie.");
        }
    }
}
