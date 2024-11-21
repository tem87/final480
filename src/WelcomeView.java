
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class WelcomeView {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Movie Ticket Booking System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLayout(new BorderLayout()); // Main layout manager

            // Title at the top (NORTH)
            JLabel welcomeLabel = new JLabel("Welcome to the Movie Ticket Booking System", SwingConstants.CENTER);
            welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
            welcomeLabel.setForeground(Color.BLUE);
            frame.add(welcomeLabel, BorderLayout.NORTH);

            // Buttons in the middle (CENTER)
            JPanel buttonPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            JButton adminButton = createStyledButton("Admin");
            JButton guestButton = createStyledButton("Guest");
            JButton loginButton = createStyledButton("Login");
            JButton signInButton = createStyledButton("Sign up");

            adminButton.addActionListener(e -> openAdminMenu(frame));
            guestButton.addActionListener(e -> openGuestMenu(frame));
            loginButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Login Feature Coming Soon!"));
            signInButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Sign-In Feature Coming Soon!"));

            gbc.gridy = 0;
            buttonPanel.add(adminButton, gbc);
            gbc.gridy = 1;
            buttonPanel.add(guestButton, gbc);
            gbc.gridy = 2;
            buttonPanel.add(loginButton, gbc);
            gbc.gridy = 3;
            buttonPanel.add(signInButton, gbc);

            frame.add(buttonPanel, BorderLayout.CENTER); // Add button panel to the center
            frame.setVisible(true);
        });
    }

    // Create a styled button for consistent design
    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setFocusPainted(false);
        return button;
    }

    // Opens the Admin menu
    private static void openAdminMenu(JFrame frame) {
        frame.getContentPane().removeAll(); // Clear the frame
        frame.setLayout(new BorderLayout());

        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(new GridLayout(6, 1, 10, 10));

        JLabel adminLabel = new JLabel("Admin Menu", SwingConstants.CENTER);
        adminLabel.setFont(new Font("Arial", Font.BOLD, 18));
        adminPanel.add(adminLabel);

        JButton viewTheatreButton = new JButton("View Theatres");
        JButton addTheatreButton = new JButton("Add Theatre");
        JButton modifyTheatreButton = new JButton("Modify Theatre");
        JButton viewMovieButton = new JButton("View Movies");
        JButton addMovieButton = new JButton("Add Movie");
        JButton modifyMovieButton = new JButton("Modify Movie");
        JButton deleteMovieButton = new JButton("Delete Movie");
        JButton viewShowtimeButton = new JButton("View Showtime");
        JButton addShowtimeButton = new JButton("Add Showtime");
        JButton modifyShowtimeButton = new JButton("Modify Showtime");
        JButton deleteShowtimeButton = new JButton("Delete Showtime");
        JButton backButton = new JButton("Back to Main Menu");

        viewTheatreButton.addActionListener(e -> showTheatre(frame));
        addTheatreButton.addActionListener(e -> addTheatre(frame));
        modifyTheatreButton.addActionListener(e -> modifyTheatre(frame));
        viewMovieButton.addActionListener(e -> showMovie(frame));
        addMovieButton.addActionListener(e -> addMovie(frame));
        modifyMovieButton.addActionListener(e -> modifyMovie(frame));
        deleteMovieButton.addActionListener(e -> deleteMovie(frame));
        viewShowtimeButton.addActionListener(e -> showShowtime(frame));
        addShowtimeButton.addActionListener(e -> addShowtime(frame));
        modifyShowtimeButton.addActionListener(e -> modifyShowtime(frame));
        deleteShowtimeButton.addActionListener(e -> deleteShowtime(frame));
        backButton.addActionListener(e -> mainMenu(frame));

        adminPanel.add(viewTheatreButton);
        adminPanel.add(addTheatreButton);
        adminPanel.add(modifyTheatreButton);
        adminPanel.add(viewMovieButton);
        adminPanel.add(addMovieButton);
        adminPanel.add(modifyMovieButton);
        adminPanel.add(deleteMovieButton);
        adminPanel.add(viewMovieButton);
        adminPanel.add(addShowtimeButton);
        adminPanel.add(modifyShowtimeButton);
        adminPanel.add(deleteShowtimeButton);
        adminPanel.add(backButton);

        frame.add(adminPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private static void showMovie(JFrame frame) {
        frame.getContentPane().removeAll(); // Clear the frame
        frame.setLayout(new BorderLayout());

        // Fetch movies from the database
        List<Movie> movies = Movie.fetchMovies();

        // Create column headers
        String[] columnNames = {"ID", "Title", "Genre", "Rating", "Synopsis", "Length"};

        // Populate movie data into a 2D array for JTable
        Object[][] data = new Object[movies.size()][columnNames.length];
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            data[i][0] = movie.getMovieID();
            data[i][1] = movie.getTitle();
            data[i][2] = movie.getGenre();
            data[i][3] = movie.getRating();
            data[i][4] = movie.getSynopsis();
            data[i][5] = movie.getLength();
        }

        // Create JTable with the movie data
        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true); // Ensure table fills the viewport
        table.setRowHeight(30); // Set row height
        table.setFont(new Font("Arial", Font.PLAIN, 14)); // Set table font
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16)); // Set header font

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        // Back button to return to Admin Menu
        JButton backButton = new JButton("Back to Admin Menu");
        backButton.setBackground(Color.DARK_GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> openAdminMenu(frame));

        // Add components to the panel
        JPanel moviesPanel = new JPanel(new BorderLayout());
        moviesPanel.add(scrollPane, BorderLayout.CENTER);
        moviesPanel.add(backButton, BorderLayout.SOUTH);

        // Add the panel to the frame
        frame.add(moviesPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }


    private static void addMovie(JFrame frame) {
        JTextField titleField = new JTextField();
        JTextField genreField = new JTextField();
        JTextField ratingField = new JTextField();
        JTextField synopsisField = new JTextField();
        JTextField lengthField = new JTextField();

        Object[] message = {
                "Title:", titleField,
                "Genre:", genreField,
                "Rating:", ratingField,
                "Synopsis:", synopsisField,
                "Length:", lengthField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Add New Movie", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String title = titleField.getText();
            String genre = genreField.getText();
            String rating = ratingField.getText();
            String synopsis = synopsisField.getText();
            String length = lengthField.getText();

            Movie newMovie = new Movie(title, genre, rating, synopsis, length);
            if (newMovie.addMovie()) {
                JOptionPane.showMessageDialog(frame, "Movie added successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to add movie.");
            }
        }
    }
    private static void modifyMovie(JFrame frame) {
        List<Movie> movies = Movie.fetchMovies();
        String[] movieTitles = movies.stream().map(Movie::getTitle).toArray(String[]::new);

        String selectedMovie = (String) JOptionPane.showInputDialog(
                frame,
                "Select a movie to modify:",
                "Modify Movie",
                JOptionPane.PLAIN_MESSAGE,
                null,
                movieTitles,
                null
        );

        if (selectedMovie != null) {
            Movie movieToEdit = movies.stream()
                    .filter(movie -> movie.getTitle().equals(selectedMovie))
                    .findFirst()
                    .orElse(null);

            if (movieToEdit != null) {
                JTextField titleField = new JTextField(movieToEdit.getTitle());
                JTextField genreField = new JTextField(movieToEdit.getGenre());
                JTextField ratingField = new JTextField(movieToEdit.getRating());
                JTextField synopsisField = new JTextField(movieToEdit.getSynopsis());
                JTextField lengthField = new JTextField(movieToEdit.getLength());

                Object[] message = {
                        "Title:", titleField,
                        "Genre:", genreField,
                        "Rating:", ratingField,
                        "Synopsis:", synopsisField,
                        "Length:", lengthField
                };

                int option = JOptionPane.showConfirmDialog(frame, message, "Modify Movie", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    movieToEdit = new Movie(
                            movieToEdit.getMovieID(),
                            titleField.getText(),
                            genreField.getText(),
                            ratingField.getText(),
                            synopsisField.getText(),
                            lengthField.getText()
                    );

                    if (movieToEdit.modifyMovie()) {
                        JOptionPane.showMessageDialog(frame, "Movie updated successfully!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to update movie.");
                    }
                }
            }
        }
    }


    private static void deleteMovie(JFrame frame) {
        List<Movie> movies = Movie.fetchMovies();
        String[] movieTitles = movies.stream().map(Movie::getTitle).toArray(String[]::new);

        String selectedMovie = (String) JOptionPane.showInputDialog(
                frame,
                "Select a movie to delete:",
                "Delete Movie",
                JOptionPane.PLAIN_MESSAGE,
                null,
                movieTitles,
                null
        );

        if (selectedMovie != null) {
            Movie movieToDelete = movies.stream()
                    .filter(movie -> movie.getTitle().equals(selectedMovie))
                    .findFirst()
                    .orElse(null);

            if (movieToDelete != null) {
                int confirmation = JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to delete " + selectedMovie + "?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmation == JOptionPane.YES_OPTION) {
                    if (Movie.deleteMovie(movieToDelete.getMovieID())) {
                        JOptionPane.showMessageDialog(frame, "Movie deleted successfully!");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to delete movie.");
                    }
                }
            }
        }
    }


    private static void showShowtime(JFrame frame) {
        JOptionPane.showMessageDialog(frame, "Soon!");
    }

    private static void showTheatre(JFrame frame) {
        JOptionPane.showMessageDialog(frame, "Soon!");
    }

    private static void addShowtime(JFrame frame) {
        JOptionPane.showMessageDialog(frame, "Soon!");
    }

    private static void addTheatre(JFrame frame) {
        JOptionPane.showMessageDialog(frame, "Soon!");
    }

    private static void modifyShowtime(JFrame frame) {
        JOptionPane.showMessageDialog(frame, "Soon!");
    }

    private static void modifyTheatre(JFrame frame) {
        JOptionPane.showMessageDialog(frame, "Soon!");
    }

    private static void deleteShowtime(JFrame frame) {
        JOptionPane.showMessageDialog(frame, "Soon!");
    }
    private static void deleteTheatre(JFrame frame) {
        JOptionPane.showMessageDialog(frame, "Soon!");
    }

    private static void showShowtimes(JFrame frame) {
        JOptionPane.showMessageDialog(frame, "Soon!");
    }


    private static void openGuestMenu(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        JPanel guestPanel = new JPanel();
        guestPanel.setLayout(new GridLayout(4, 1, 10, 10));

        JLabel guestLabel = new JLabel("Guest Menu", SwingConstants.CENTER);
        guestLabel.setFont(new Font("Arial", Font.BOLD, 18));
        guestPanel.add(guestLabel);

        JButton viewMoviesButton = new JButton("View Movies");
        JButton viewShowtimesButton = new JButton("View Showtimes");
        JButton backButton = new JButton("Back to Main Menu");

        viewMoviesButton.addActionListener(e -> showMovie(frame));
        viewShowtimesButton.addActionListener(e -> showShowtimes(frame));
        backButton.addActionListener(e -> mainMenu(frame));

        guestPanel.add(viewMoviesButton);
        guestPanel.add(viewShowtimesButton);
        guestPanel.add(backButton);

        frame.add(guestPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private static void mainMenu(JFrame frame) {
        frame.getContentPane().removeAll();
        main(new String[]{}); // Restart main
    }

}

