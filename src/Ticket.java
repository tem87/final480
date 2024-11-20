import java.time.LocalDateTime;

public class Ticket {
    private int ticketID;
    private Seat seat;
    private Showtime showtime;
    private User user;
    private TicketState state;
    private Receipt receipt;

    private static int ticketCounter = 1000;

    // Constructor
    public Ticket(Seat seat, Showtime showtime, User user) {
        this.ticketID = ticketCounter++;
        this.seat = seat;
        this.showtime = showtime;
        this.user = user;
        this.state = TicketState.AVAILABLE; // Initial state
    }

    // Getters and Setters
    public int getTicketID() {
        return ticketID;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Showtime getShowtime() {
        return showtime;
    }

    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TicketState getState() {
        return state;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    // Attempt to implement the state diagram thingy, might have to change this.
    // State Transition Method
    public boolean transitionState(TicketState nextState) {
        if (this.state.canTransitionTo(nextState)) {
            this.state = nextState;
            handleStateActions(nextState);
            return true;
        }
        return false; 
    }

    // Handle actions for various states
    private void handleStateActions(TicketState state) {
        switch (state) {
            case BOOKED:
                seat.reserveSeat();
                System.out.println("Ticket reserved for user: " + user.getName());
                break;
            case AVAILABLE:
                seat.releaseSeat();
                System.out.println("Ticket released back to available pool.");
                break;
            case PAID:
                System.out.println("Payment completed for ticket ID: " + ticketID);
                break;
            case REFUNDED:
                System.out.println("Refund issued for ticket ID: " + ticketID);
                break;
            case USED:
                System.out.println("Ticket ID: " + ticketID + " used for showtime.");
                break;
        }
    }

    public String getTicketDetails() {
        return "Ticket ID: " + ticketID +
               "\nShowtime: " + showtime.getDateTime() +
               "\nSeat: " + seat.getSeatNumber() +
               "\nUser: " + user.getName() +
               "\nStatus: " + state;
    }

    // Enum for the Ticket States
    public enum TicketState {
        AVAILABLE, // Ticket is available for booking
        BOOKED,    // Ticket is reserved but not yet paid
        PAID,      // Ticket is paid
        USED,      // Ticket is used
        REFUNDED;  // Ticket is refunded

        // Check if a transition to the given state is valid
        public boolean canTransitionTo(TicketState nextState) {
            switch (this) {
                case AVAILABLE:
                    return nextState == BOOKED;
                case BOOKED:
                    return nextState == PAID || nextState == AVAILABLE;
                case PAID:
                    return nextState == USED || nextState == REFUNDED;
                case REFUNDED:
                    return nextState == AVAILABLE;
                case USED:
                    return false; // FINAL STATE
                default:
                    return false;
            }
        }
    }
}
