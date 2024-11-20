import java.util.Scanner;

public class Main {
    public enum UserRole {
        ADMIN, GUEST, LOGIN_IN, SIGN_IN
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserRole userRole = selectUserRole(scanner);

        while (true) {
            System.out.println("\nWelcome to the Movie Ticket Booking System!");
            switch (userRole) {
                case ADMIN:
                    Menu.showAdminMenu(scanner);
                    break;
                case GUEST:
                    Menu.showGuestMenu(scanner);
                    break;
                case LOGIN_IN:
                    Menu.showLogin(scanner);
                    break;
                case SIGN_IN:
                    Menu.showSign(scanner);
                    break;
                default:
                    System.out.println("Invalid role. Exiting system.");
                    scanner.close();
                    return;
            }
        }
    }

    // Moved selectUserRole from Menu to Main
    public static UserRole selectUserRole(Scanner scanner) {
        System.out.println("Please select your role:");
        System.out.println("1. Admin");
        System.out.println("2. Continue as a guest");
        System.out.println("3. Login");
        System.out.println("4. Want to sign in?");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                return UserRole.ADMIN;
            case 2:
                return UserRole.GUEST;
            case 3:
                return UserRole.LOGIN_IN;
            case 4:
                return UserRole.SIGN_IN;
            default:
                System.out.println("Invalid choice. Defaulting to Guest.");
                return UserRole.GUEST;
        }
    }
}
