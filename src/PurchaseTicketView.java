import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PurchaseTicketView {

    public static void showPurchaseTicketView(JFrame frame, Runnable backToMenuCallback) {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        JLabel titleLabel = new JLabel("Purchase Ticket", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel);

        // Dropdowns for Theatre, Movie, and Showtime
        JComboBox<String> theatreDropdown = new JComboBox<>();
        JComboBox<String> movieDropdown = new JComboBox<>();
        JComboBox<String> showtimeDropdown = new JComboBox<>();

        // Populate Theatre Dropdown
        List<Theatre> theatres = Theatre.fetchTheaters();
        for (Theatre theatre : theatres) {
            theatreDropdown.addItem(theatre.getName() + " - " + theatre.getLocation());
        }

        // Listeners for dropdown dependencies
        theatreDropdown.addActionListener(e -> {
            movieDropdown.removeAllItems();
            showtimeDropdown.removeAllItems();

            int selectedTheatreIndex = theatreDropdown.getSelectedIndex();
            if (selectedTheatreIndex >= 0) {
                Theatre selectedTheatre = theatres.get(selectedTheatreIndex);
                List<Movie> movies = Movie.fetchMovies();

                for (Movie movie : movies) {
                    movieDropdown.addItem(movie.getTitle());
                }
            }
        });

        movieDropdown.addActionListener(e -> {
            showtimeDropdown.removeAllItems();

            int selectedTheatreIndex = theatreDropdown.getSelectedIndex();
            int selectedMovieIndex = movieDropdown.getSelectedIndex();

            if (selectedTheatreIndex >= 0 && selectedMovieIndex >= 0) {
                Theatre selectedTheatre = theatres.get(selectedTheatreIndex);
                Movie selectedMovie = Movie.fetchMovies().get(selectedMovieIndex);

                List<Showtime> showtimes = Showtime.fetchShowtimes();
                for (Showtime showtime : showtimes) {
                    if (showtime.getMovieID() == selectedMovie.getMovieID() && showtime.getTheaterID() == selectedTheatre.getTheatreID()) {
                        showtimeDropdown.addItem(showtime.getDateTime().toString());
                    }
                }
            }
        });

        // Add dropdowns and labels to panel
        panel.add(new JLabel("Select Theatre:", SwingConstants.CENTER));
        panel.add(theatreDropdown);

        panel.add(new JLabel("Select Movie:", SwingConstants.CENTER));
        panel.add(movieDropdown);

        panel.add(new JLabel("Select Showtime:", SwingConstants.CENTER));
        panel.add(showtimeDropdown);

        // Buttons
        JButton selectSeatsButton = new JButton("Select Seats");
        JButton backButton = new JButton("Back to Menu");

        selectSeatsButton.addActionListener(e -> {
            int selectedTheatreIndex = theatreDropdown.getSelectedIndex();
            int selectedMovieIndex = movieDropdown.getSelectedIndex();
            int selectedShowtimeIndex = showtimeDropdown.getSelectedIndex();

            if (selectedTheatreIndex >= 0 && selectedMovieIndex >= 0 && selectedShowtimeIndex >= 0) {
                Theatre selectedTheatre = theatres.get(selectedTheatreIndex);
                Movie selectedMovie = Movie.fetchMovies().get(selectedMovieIndex);
                Showtime selectedShowtime = Showtime.fetchShowtimes().get(selectedShowtimeIndex);

                // Navigate to Seat Selection View
                SeatSelectionView.showSeats(frame, selectedShowtime.getShowtimeID());
            } else {
                JOptionPane.showMessageDialog(frame, "Please select Theatre, Movie, and Showtime before selecting seats.");
            }
        });

        backButton.addActionListener(e -> backToMenuCallback.run());

        panel.add(selectSeatsButton);
        panel.add(backButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }
}
