import javax.swing.*;
import java.awt.*;
import java.util.List;

public class WelcomeView {

    public static void showMainMenu() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Movie Ticket Booking System :)");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLayout(new BorderLayout());

            JLabel welcomeLabel = new JLabel("MOVIE TICKET BOOKING SYSTEM", SwingConstants.CENTER);
            welcomeLabel.setFont(new Font("Georgia", Font.BOLD, 28));
            welcomeLabel.setForeground(new Color(0, 51, 102)); // Dark blue
            welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
            frame.add(welcomeLabel, BorderLayout.NORTH);

            JPanel buttonPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            JButton adminButton = createStyledButton("Admin");
            JButton guestButton = createStyledButton("Guest");
            JButton loginButton = createStyledButton("Login");
            JButton signInButton = createStyledButton("Sign up");

            adminButton.addActionListener(e -> AdminView.showAdminLogin(frame));
            guestButton.addActionListener(e -> GuestView.openGuestMenu(frame));
            loginButton.addActionListener(e -> loginForm(frame));
            signInButton.addActionListener(e -> showSignUpForm(frame));

            gbc.gridy = 0;
            buttonPanel.add(adminButton, gbc);
            gbc.gridy = 1;
            buttonPanel.add(guestButton, gbc);
            gbc.gridy = 2;
            buttonPanel.add(loginButton, gbc);
            gbc.gridy = 3;
            buttonPanel.add(signInButton, gbc);

            frame.add(buttonPanel, BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }

    private static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(200, 162, 200));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 162, 200), 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(216, 191, 216));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(200, 162, 200));
            }
        });
        return button;
    }

    private static void showSignUpForm(JFrame frame) {
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField phoneField = new JTextField();
        JTextField addressField = new JTextField();

        Object[] message = {
                "Full Name:", nameField,
                "Email:", emailField,
                "Password:", passwordField,
                "Phone Number (e.g. 585-589-5236):", phoneField,
                "Address:", addressField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Sign Up", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String phone = phoneField.getText();
            String address = addressField.getText();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Name, Email, and Password are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User newUser = new User(name, email, password, phone, address);

            if (newUser.saveToDatabase()) {
                JOptionPane.showMessageDialog(frame, "Sign Up Successful! Welcome, " + name + "Login now to access all the features");
            } else {
                JOptionPane.showMessageDialog(frame, "Sign Up Failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void loginForm(JFrame frame) {
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] message = {
                "Email:", emailField,
                "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            User loggedInUser = User.authenticate(email, password);
            if (loggedInUser != null) {
                JOptionPane.showMessageDialog(frame, "Login Successful! Welcome, " + loggedInUser.getName());
                showUserMenu(frame, loggedInUser);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid email or password. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void showUserMenu(JFrame frame, User user) {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        JLabel userLabel = new JLabel("Welcome, " + user.getName(), SwingConstants.CENTER);
        userLabel.setFont(new Font("Arial", Font.BOLD, 18));
        userLabel.setForeground(new Color(0, 51, 102));
        frame.add(userLabel, BorderLayout.NORTH);

        JPanel userMenuPanel = new JPanel(new GridLayout(0, 1, 10, 10));

        JButton viewMoviesButton = new JButton("View Movies");
        JButton viewShowtimesButton = new JButton("View Showtimes");
        JButton bookTicketButton = new JButton("Book Ticket");
        JButton logoutButton = new JButton("Logout");

        // Action listener to show seating map when the "Book Ticket" button is clicked
        bookTicketButton.addActionListener(e -> {
            int showtimeId = 1; // This will come from the selected showtime
            showSeatMap(frame, showtimeId, user);
        });

        logoutButton.addActionListener(e -> showMainMenu());

        userMenuPanel.add(viewMoviesButton);
        userMenuPanel.add(viewShowtimesButton);
        userMenuPanel.add(bookTicketButton);
        userMenuPanel.add(logoutButton);

        frame.add(userMenuPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    public static void showSeatMap(JFrame frame, int showtimeId, User user) {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        // Fetch the list of seats for this showtime
        List<Seat> seats = Seat.fetchSeatsByShowtime(showtimeId);

        // Panel to display the heading
        JPanel headingPanel = new JPanel();
        JLabel headingLabel = new JLabel("Select Seats", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingLabel.setForeground(new Color(3, 51, 102)); // Dark blue color for the heading
        headingPanel.add(headingLabel);
        frame.add(headingPanel, BorderLayout.NORTH);

        // Panel to display the seating map with GridBagLayout for better control
        JPanel seatMapPanel = new JPanel();
        seatMapPanel.setLayout(new GridBagLayout());  // Using GridBagLayout for more flexible positioning

        // GridBagConstraints to control layout and padding
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Adjusted space around each seat button
        gbc.fill = GridBagConstraints.CENTER; // Center align seat buttons within their grid cell

        // Add seat buttons to the panel
        int seatCounter = 0;
        int maxSeatsInRow = 4; // You want 4 seats per row
        for (Seat seat : seats) {
            JButton seatButton = new JButton(seat.getSeatNumber());
            seatButton.setFont(new Font("Arial", Font.PLAIN, 12));  // Adjust font size for better fit
            seatButton.setPreferredSize(new Dimension(30, 30));  // Keep buttons smaller (tiny squares)

            // Set color based on seat availability
            if ("Available".equalsIgnoreCase(seat.getStatus())) {
                seatButton.setBackground(Color.GREEN);
            } else {
                seatButton.setBackground(Color.RED);
            }

            // Action listener to handle booking and releasing seats
            seatButton.addActionListener(e -> {
                if ("Available".equalsIgnoreCase(seat.getStatus())) {
                    boolean reserved = seat.reserveSeat(user.getUserId());  // Assuming user has a userId field
                    if (reserved) {
                        seatButton.setBackground(Color.RED);
                        JOptionPane.showMessageDialog(frame, "Seat " + seat.getSeatNumber() + " booked successfully.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to book seat " + seat.getSeatNumber() + ".", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    boolean released = seat.releaseSeat();
                    if (released) {
                        seatButton.setBackground(Color.GREEN);
                        JOptionPane.showMessageDialog(frame, "Seat " + seat.getSeatNumber() + " released successfully.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to release seat " + seat.getSeatNumber() + ".", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            // Check if it's the last row with fewer than 4 seats
            if ((seatCounter / maxSeatsInRow) == (seats.size() / maxSeatsInRow)) {
                // Calculate spaces to center the remaining seats
                int emptySpace = (maxSeatsInRow - (seats.size() % maxSeatsInRow)) / 2;
                gbc.gridx = emptySpace;
            } else {
                gbc.gridx = seatCounter % maxSeatsInRow;
            }

            // Add the seat button to the grid
            gbc.gridy = seatCounter / maxSeatsInRow;

            seatMapPanel.add(seatButton, gbc);

            // Increment the seat counter
            seatCounter++;
        }

        // Add the seat map panel to the frame
        frame.add(seatMapPanel, BorderLayout.CENTER);

        // Panel for navigation buttons (Back to User Menu and Proceed to Payment)
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton backButton = new JButton("Back to User Menu");
        backButton.addActionListener(e -> showUserMenu(frame, user));

        JButton paymentButton = new JButton("Proceed to Payment");
        paymentButton.addActionListener(e -> {
            // Later define the payment view logic
            JOptionPane.showMessageDialog(frame, "Proceeding to payment view.");
        });

        buttonPanel.add(backButton);
        buttonPanel.add(paymentButton);

        // Add the navigation buttons panel to the frame
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.revalidate();  // Revalidate the frame layout
        frame.repaint();     // Repaint the frame to update UI
    }






}
