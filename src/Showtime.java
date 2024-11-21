import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Showtime {
    private int showtimeID;
    private int movieID;
    private int theaterID;
    private LocalDateTime dateTime;
    private int maxSeats;

    // Constructor for existing showtimes
    public Showtime(int showtimeID, int movieID, int theaterID, LocalDateTime dateTime, int maxSeats) {
        this.showtimeID = showtimeID;
        this.movieID = movieID;
        this.theaterID = theaterID;
        this.dateTime = dateTime;
        this.maxSeats = maxSeats;
    }

    // Constructor for new showtimes
    public Showtime(int movieID, int theaterID, LocalDateTime dateTime, int maxSeats) {
        this.movieID = movieID;
        this.theaterID = theaterID;
        this.dateTime = dateTime;
        this.maxSeats = maxSeats;
    }

    // Getters
    public int getShowtimeID() {
        return showtimeID;
    }

    public int getMovieID() {
        return movieID;
    }

    public int getTheaterID() {
        return theaterID;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public static List<Showtime> fetchShowtimes() {
        List<Showtime> showtimes = new ArrayList<>();
        String query = "SELECT * FROM Showtime";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int showtimeID = resultSet.getInt("showtime_id");
                int movieID = resultSet.getInt("movie_id");
                int theaterID = resultSet.getInt("theater_id");
                LocalDateTime dateTime = resultSet.getTimestamp("start_time").toLocalDateTime();
                int maxSeats = resultSet.getInt("max_seats");

                showtimes.add(new Showtime(showtimeID, movieID, theaterID, dateTime, maxSeats));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching showtimes: " + e.getMessage());
        }

        return showtimes;
    }

    public boolean addShowtime() {
        String query = "INSERT INTO Showtime (movie_id, theater_id, start_time, max_seats) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, movieID);
            preparedStatement.setInt(2, theaterID);
            preparedStatement.setTimestamp(3, java.sql.Timestamp.valueOf(dateTime));
            preparedStatement.setInt(4, maxSeats);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error adding showtime: " + e.getMessage());
            return false;
        }
    }

    public boolean modifyShowtime() {
        String query = "UPDATE Showtime SET movie_id = ?, theater_id = ?, start_time = ?, max_seats = ? WHERE showtime_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, movieID);
            preparedStatement.setInt(2, theaterID);
            preparedStatement.setTimestamp(3, java.sql.Timestamp.valueOf(dateTime));
            preparedStatement.setInt(4, maxSeats);
            preparedStatement.setInt(5, showtimeID);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating showtime: " + e.getMessage());
            return false;
        }
    }

    public static boolean deleteShowtime(int showtimeID) {
        String query = "DELETE FROM Showtime WHERE showtime_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, showtimeID);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting showtime: " + e.getMessage());
            return false;
        }
    }

    @Override
    public String toString() {
        return "Showtime{" +
                "showtimeID=" + showtimeID +
                ", movieID=" + movieID +
                ", theaterID=" + theaterID +
                ", dateTime=" + dateTime +
                ", maxSeats=" + maxSeats +
                '}';
    }
}
