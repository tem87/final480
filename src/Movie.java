import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Movie {
    private int movieID;
    private String title;
    private String genre;
    private String rating;
    private String synopsis;
    private String length;

    // Constructor for retrieving existing movies
    public Movie(int movieID, String title, String genre, String rating, String synopsis, String length) {
        this.movieID = movieID;
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.synopsis = synopsis;
        this.length = length;
    }

    // Constructor for creating new movies (without ID)
    public Movie(String title, String genre, String rating, String synopsis, String length) {
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.synopsis = synopsis;
        this.length = length;
    }

    // Getters
    public int getMovieID() {
        return movieID;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getRating() {
        return rating;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getLength() {
        return length;
    }

    // Fetch movies from the database
    public static List<Movie> fetchMovies() {
        List<Movie> movies = new ArrayList<>();
        String query = "SELECT * FROM Movie";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int movieID = resultSet.getInt("movie_id");
                String title = resultSet.getString("title");
                String genre = resultSet.getString("genre");
                String rating = resultSet.getString("rating");
                String synopsis = resultSet.getString("synopsis");
                String length = resultSet.getString("length");

                movies.add(new Movie(movieID, title, genre, rating, synopsis, length));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching movies: " + e.getMessage());
        }

        return movies;
    }

    // Insert a new movie into the database
    public boolean insertMovie() {
        String query = "INSERT INTO Movie (title, genre, rating, synopsis, length) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, genre);
            preparedStatement.setString(3, rating);
            preparedStatement.setString(4, synopsis);
            preparedStatement.setString(5, length);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if insertion was successful

        } catch (SQLException e) {
            System.err.println("Error inserting movie: " + e.getMessage());
            return false;
        }
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movieID=" + movieID +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", rating=" + rating +
                ", synopsis='" + synopsis + '\'' +
                ", length='" + length + '\'' +
                '}';
    }
}
