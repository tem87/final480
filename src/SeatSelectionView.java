import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SeatSelectionView {

    public static void showSeats(JFrame frame, int showtimeId) {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        List<Seat> seats = Seat.fetchSeatsByShowtime(showtimeId);
        String[] columnNames = {"Seat ID", "Seat Number", "Status"};

        Object[][] data = new Object[seats.size()][columnNames.length];
        for (int i = 0; i < seats.size(); i++) {
            Seat seat = seats.get(i);
            data[i][0] = seat.getSeatID();
            data[i][1] = seat.getSeatNumber();
            data[i][2] = seat.getStatus();
        }

        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));

        JScrollPane scrollPane = new JScrollPane(table);

        JButton backButton = new JButton("Back to Showtime Menu");
        backButton.addActionListener(e -> ShowtimeView.showShowtime(frame, () -> GuestView.openGuestMenu(frame)));
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);

        frame.add(panel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    public static void addSeat(JFrame frame, int showtimeId) {
        JTextField seatNumberField = new JTextField();
        JComboBox<String> statusField = new JComboBox<>(new String[]{"Available", "Reserved"});

        Object[] message = {
                "Seat Number:", seatNumberField,
                "Status:", statusField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Add New Seat", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String seatNumber = seatNumberField.getText();
                String status = (String) statusField.getSelectedItem();

                Seat newSeat = new Seat(showtimeId, seatNumber, status);
                if (newSeat.addSeat()) {
                    JOptionPane.showMessageDialog(frame, "Seat added successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to add seat.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Invalid input. Please try again.");
            }
        }
    }

    public static void modifySeat(JFrame frame, int showtimeId) {
        List<Seat> seats = Seat.fetchSeatsByShowtime(showtimeId);
        String[] seatNumbers = seats.stream().map(Seat::getSeatNumber).toArray(String[]::new);

        String selectedSeatNumber = (String) JOptionPane.showInputDialog(
                frame,
                "Select a seat to modify:",
                "Modify Seat",
                JOptionPane.PLAIN_MESSAGE,
                null,
                seatNumbers,
                null
        );

        if (selectedSeatNumber != null) {
            Seat seatToEdit = seats.stream()
                    .filter(s -> s.getSeatNumber().equals(selectedSeatNumber))
                    .findFirst()
                    .orElse(null);

            if (seatToEdit != null) {
                JTextField seatNumberField = new JTextField(seatToEdit.getSeatNumber());
                JComboBox<String> statusField = new JComboBox<>(new String[]{"Available", "Reserved"});
                statusField.setSelectedItem(seatToEdit.getStatus());

                Object[] message = {
                        "Seat Number:", seatNumberField,
                        "Status:", statusField
                };

                int option = JOptionPane.showConfirmDialog(frame, message, "Modify Seat", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    try {
                        String seatNumber = seatNumberField.getText();
                        String status = (String) statusField.getSelectedItem();

                        seatToEdit = new Seat((Integer) seatToEdit.getSeatID(), showtimeId, seatNumber, status);
                        if (seatToEdit.modifySeat()) {
                            JOptionPane.showMessageDialog(frame, "Seat updated successfully!");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Failed to update seat.");
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(frame, "Invalid input. Please try again.");
                    }
                }
            }
        }
    }

    public static void deleteSeat(JFrame frame, int showtimeId) {
        List<Seat> seats = Seat.fetchSeatsByShowtime(showtimeId);
        String[] seatNumbers = seats.stream().map(Seat::getSeatNumber).toArray(String[]::new);

        String selectedSeatNumber = (String) JOptionPane.showInputDialog(
                frame,
                "Select a seat to delete:",
                "Delete Seat",
                JOptionPane.PLAIN_MESSAGE,
                null,
                seatNumbers,
                null
        );

        if (selectedSeatNumber != null) {
            int confirmation = JOptionPane.showConfirmDialog(
                    frame,
                    "Are you sure you want to delete seat " + selectedSeatNumber + "?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmation == JOptionPane.YES_OPTION) {
                Seat seatToDelete = seats.stream()
                        .filter(s -> s.getSeatNumber().equals(selectedSeatNumber))
                        .findFirst()
                        .orElse(null);

                if (seatToDelete != null && seatToDelete.deleteSeat()) {
                    JOptionPane.showMessageDialog(frame, "Seat deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to delete seat.");
                }
            }
        }
    }
}
