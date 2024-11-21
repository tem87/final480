import javax.swing.*;
import java.awt.*;

public class AdminView {
    public static void showAdminLogin(JFrame frame) {
        JPanel loginPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        JLabel usernameLabel = new JLabel("username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("password:");
        JPasswordField passwordField = new JPasswordField();

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);

        int option = JOptionPane.showConfirmDialog(
                frame, loginPanel, "Admin Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (validateAdminCredentials(username, password)) {
                openAdminMenu(frame);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password. Access denied.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static boolean validateAdminCredentials(String username, String password) {
        String adminUsername = "admin";
        String adminPassword = "gojo123";

        return username.equals(adminUsername) && password.equals(adminPassword);
    }

    public static void openAdminMenu(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(new GridLayout(0, 1, 10, 10));


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

        viewTheatreButton.addActionListener(e -> TheatreView.showTheatre(frame));
        addTheatreButton.addActionListener(e -> TheatreView.addTheatre(frame));
        modifyTheatreButton.addActionListener(e -> TheatreView.modifyTheatre(frame));
        viewMovieButton.addActionListener(e -> MovieView.showMovie(frame));
        addMovieButton.addActionListener(e -> MovieView.addMovie(frame));
        modifyMovieButton.addActionListener(e -> MovieView.modifyMovie(frame));
        deleteMovieButton.addActionListener(e -> MovieView.deleteMovie(frame));
        viewShowtimeButton.addActionListener(e -> ShowtimeView.showShowtime(frame));
        addShowtimeButton.addActionListener(e -> ShowtimeView.addShowtime(frame));
        modifyShowtimeButton.addActionListener(e -> ShowtimeView.modifyShowtime(frame));
        deleteShowtimeButton.addActionListener(e -> ShowtimeView.deleteShowtime(frame));
        backButton.addActionListener(e -> WelcomeView.showMainMenu());

        adminPanel.add(viewTheatreButton);
        adminPanel.add(addTheatreButton);
        adminPanel.add(modifyTheatreButton);
        adminPanel.add(viewMovieButton);
        adminPanel.add(addMovieButton);
        adminPanel.add(modifyMovieButton);
        adminPanel.add(deleteMovieButton);
        adminPanel.add(viewShowtimeButton);
        adminPanel.add(addShowtimeButton);
        adminPanel.add(modifyShowtimeButton);
        adminPanel.add(deleteShowtimeButton);
        adminPanel.add(backButton);

        frame.add(adminPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }
}
