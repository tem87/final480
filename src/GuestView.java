import javax.swing.*;
import java.awt.*;

public class GuestView {

    public static void openGuestMenu(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        JPanel guestPanel = new JPanel();
        guestPanel.setLayout(new GridLayout(0, 1, 10, 10));

        JLabel guestPanelLabel = new JLabel("Guest Menu", SwingConstants.CENTER);
        guestPanelLabel.setFont(new Font("Arial", Font.BOLD, 18));
        guestPanel.add(guestPanelLabel);

        JButton viewTheatreButton = new JButton("View Theatres");
        JButton viewMovieButton = new JButton("View Movies");
        JButton viewShowtimeButton = new JButton("View Showtimes");
        JButton purchaseTicketButton = new JButton("Purchase Ticket");
        JButton backButton = new JButton("Back to Main Menu");

        // Update callbacks for the new changes
        viewTheatreButton.addActionListener(e ->
                TheatreView.showTheatre(frame, () -> GuestView.openGuestMenu(frame))
        );
        viewMovieButton.addActionListener(e ->
                MovieView.showMovie(frame, () -> GuestView.openGuestMenu(frame))
        );
        viewShowtimeButton.addActionListener(e ->
                ShowtimeView.showShowtime(frame, () -> GuestView.openGuestMenu(frame))
        );
        purchaseTicketButton.addActionListener(e ->
                JOptionPane.showMessageDialog(frame, "Purchase Ticket Feature Coming Soon!")
        );
        backButton.addActionListener(e ->
                WelcomeView.showMainMenu()
        );

        guestPanel.add(viewTheatreButton);
        guestPanel.add(viewMovieButton);
        guestPanel.add(viewShowtimeButton);
        guestPanel.add(purchaseTicketButton);
        guestPanel.add(backButton);
        frame.add(guestPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }
}
