import javax.swing.*;
import java.awt.*;

public class PaymentView {

    public static void showPaymentView(JFrame frame, Runnable backToPreviousView, Theatre theatre, Movie movie, Showtime showtime, Seat seat, Runnable backToMenuCallback) {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        JLabel titleLabel = new JLabel("Select Payment Method", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel);

        // Payment Method Dropdown
        JComboBox<String> paymentMethodDropdown = new JComboBox<>(new String[]{"Credit Card", "Debit Card"});
        panel.add(new JLabel("Payment Method:", SwingConstants.CENTER));
        panel.add(paymentMethodDropdown);

        // Payment Details
        JTextField nameField = new JTextField();
        JTextField cardNumberField = new JTextField();
        JPasswordField cvvField = new JPasswordField();
        JTextField expirationDateField = new JTextField();

        panel.add(new JLabel("Name:", SwingConstants.CENTER));
        panel.add(nameField);
        panel.add(new JLabel("Card Number:", SwingConstants.CENTER));
        panel.add(cardNumberField);
        panel.add(new JLabel("CVV:", SwingConstants.CENTER));
        panel.add(cvvField);
        panel.add(new JLabel("Expiration Date (MM/YY):", SwingConstants.CENTER));
        panel.add(expirationDateField);

        // Buttons
        JButton confirmButton = new JButton("Confirm Payment");
        JButton backButton = new JButton("Back to Previous");

        confirmButton.addActionListener(e -> {
            String selectedMethod = (String) paymentMethodDropdown.getSelectedItem();
            String name = nameField.getText();
            String cardNumber = cardNumberField.getText();
            String cvv = new String(cvvField.getPassword());
            String expirationDate = expirationDateField.getText();

            if (validateInputs(selectedMethod, name, cardNumber, cvv, expirationDate)) {
                JOptionPane.showMessageDialog(frame, "Payment Successful!\n" +
                        "Theatre: " + theatre.getName() +
                        "\nMovie: " + movie.getTitle() +
                        "\nShowtime: " + showtime.getDateTime() +
                        "\nSeat: " + seat.getSeatNumber() +
                        "\nPayment Method: " + selectedMethod);
                backToMenuCallback.run(); // Return to main menu after successful payment
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Payment Details. Please try again.");
            }
        });

        backButton.addActionListener(e -> backToPreviousView.run());

        panel.add(confirmButton);
        panel.add(backButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    // Validate inputs for payment details
    private static boolean validateInputs(String method, String name, String cardNumber, String cvv, String expirationDate) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        if (cardNumber == null || !cardNumber.matches("\\d{16}")) { // Must be 16 digits
            return false;
        }
        if (cvv == null || !cvv.matches("\\d{3}")) { // Must be 3 digits
            return false;
        }
        if (expirationDate == null || !expirationDate.matches("(0[1-9]|1[0-2])/\\d{2}")) { // MM/YY format
            return false;
        }
        return true;
    }
}
