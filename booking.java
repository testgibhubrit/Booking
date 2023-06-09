package dk;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Admin extends User {
    public Admin(String username, String password) {
        super(username, password);
    }
}

class Flight {
    private String flightNumber;
    private String departureDate;
    private String departureTime;
    private int availableSeats;

    public Flight(String flightNumber, String departureDate, String departureTime, int availableSeats) {
        this.flightNumber = flightNumber;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.availableSeats = availableSeats;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}

class Booking {
    private User user;
    private Flight flight;

    public Booking(User user, Flight flight) {
        this.user = user;
        this.flight = flight;
    }

    public User getUser() {
        return user;
    }

    public Flight getFlight() {
        return flight;
    }
}

class FlightBookingSystem {
    private List<User> users;
    private List<Flight> flights;
    private List<Booking> bookings;
    protected User loggedInUser;

    public FlightBookingSystem() {
        users = new ArrayList<>();
        flights = new ArrayList<>();
        bookings = new ArrayList<>();
    }

    public void signUp(String username, String password) {
        User newUser = new User(username, password);
        users.add(newUser);
        System.out.println("User signed up successfully.");
    }

    public void login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loggedInUser = user;
                System.out.println("Login successful.");
                return;
            }
        }
        System.out.println("Invalid username or password.");
    }

    public void adminLogin(String username, String password) {
        if (username.equals("admin") && password.equals("admin123")) {
            loggedInUser = new Admin(username, password);
            System.out.println("Logged in as Admin");
        } else {
            System.out.println("Invalid username or password");
        }
    }

    public void logout() {
        loggedInUser = null;
        System.out.println("Logged out successfully.");
    }

    public void addFlight(String flightNumber, String departureDate, String departureTime, int availableSeats) {
        Flight newFlight = new Flight(flightNumber, departureDate, departureTime, availableSeats);
        flights.add(newFlight);
        System.out.println("Flight added successfully.");
    }

    public void removeFlight(String flightNumber) {
        Flight foundFlight = null;
        for (Flight flight : flights) {
            if (flight.getFlightNumber().equals(flightNumber)) {
                foundFlight = flight;
                break;
            }
        }
        if (foundFlight != null) {
            flights.remove(foundFlight);
            System.out.println("Flight removed successfully.");
        } else {
            System.out.println("Flight not found.");
        }
    }

    public void searchFlights(String departureDate, String departureTime) {
        System.out.println("Flights available on " + departureDate + " at " + departureTime + ":");
        for (Flight flight : flights) {
            if (flight.getDepartureDate().equals(departureDate) && flight.getDepartureTime().equals(departureTime)) {
                System.out.println(flight.getFlightNumber() + " (" + flight.getAvailableSeats() + " seats available)");
            }
        }
    }

    public void bookFlight(String flightNumber) {
        Flight foundFlight = null;
        for (Flight flight : flights) {
            if (flight.getFlightNumber().equals(flightNumber)) {
                foundFlight = flight;
                break;
            }
        }
        if (foundFlight != null) {
            if (foundFlight.getAvailableSeats() > 0) {
                Booking newBooking = new Booking(loggedInUser, foundFlight);
                bookings.add(newBooking);
                foundFlight.setAvailableSeats(foundFlight.getAvailableSeats() - 1);
                System.out.println("Flight booked successfully.");
            } else {
                System.out.println("No seats available on this flight.");
            }
        } else {
            System.out.println("Flight not found.");
        }
    }

    public void viewBookings() {
        System.out.println("Bookings:");
        for (Booking booking : bookings) {
            System.out.println("User: " + booking.getUser().getUsername() +
                    " - Flight: " + booking.getFlight().getFlightNumber() +
                    " - Departure Date: " + booking.getFlight().getDepartureDate() +
                    " - Departure Time: " + booking.getFlight().getDepartureTime());
        }
    }
}

public class FlightBookingApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FlightBookingSystem bookingSystem = new FlightBookingSystem();
        System.out.println("---------------------------------------------------");
        System.out.println("--------------WELCOME TO DK AIRLINE SERVICES--------");
        System.out.println("-----------------------------------------------------");
        boolean exit = false;
        while (!exit) {
        	System.out.println("----------------------------------");
            System.out.println("|***Flight Booking Application***|");
            System.out.println("----------------------------------");
            System.out.println("------------------------------");
            System.out.println("|1. Sign Up                  |");
            System.out.println("|2. Login                    |");
            System.out.println("|3. Admin Login              |");
            System.out.println("|4. Add Flight               |");
            System.out.println("|5. Remove Flight            |");
            System.out.println("|6. Search Flights           |");
            System.out.println("|7. Book Flight              |");
            System.out.println("|8. View Bookings            |");
            System.out.println("|9. Logout                   |");
            System.out.println("|10. Exit                    |");
            System.out.println("------------------------------");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String signupUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String signupPassword = scanner.nextLine();
                    bookingSystem.signUp(signupUsername, signupPassword);
                    break;
                case 2:
                    System.out.print("Enter username: ");
                    String loginUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();
                    bookingSystem.login(loginUsername, loginPassword);
                    break;
                case 3:
                    System.out.print("Enter admin username: ");
                    String adminUsername = scanner.nextLine();
                    System.out.print("Enter admin password: ");
                    String adminPassword = scanner.nextLine();
                    bookingSystem.adminLogin(adminUsername, adminPassword);
                    break;
                case 4:
                    if (bookingSystem.loggedInUser instanceof Admin) {
                        System.out.print("Enter flight number: ");
                        String flightNumber = scanner.nextLine();
                        System.out.print("Enter departure date: ");
                        String departureDate = scanner.nextLine();
                        System.out.print("Enter departure time: ");
                        String departureTime = scanner.nextLine();
                        System.out.print("Enter available seats: ");
                        int availableSeats = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        bookingSystem.addFlight(flightNumber, departureDate, departureTime, availableSeats);
                    } else {
                        System.out.println("You are not authorized to perform this operation.");
                    }
                    break;
                case 5:
                    if (bookingSystem.loggedInUser instanceof Admin) {
                        System.out.print("Enter flight number to remove: ");
                        String flightNumberToRemove = scanner.nextLine();
                        bookingSystem.removeFlight(flightNumberToRemove);
                    } else {
                        System.out.println("You are not authorized to perform this operation.");
                    }
                    break;
                case 6:
                    System.out.print("Enter departure date: ");
                    String searchDepartureDate = scanner.nextLine();
                    System.out.print("Enter departure time: ");
                    String searchDepartureTime = scanner.nextLine();
                    bookingSystem.searchFlights(searchDepartureDate, searchDepartureTime);
                    break;
                case 7:
                    if (bookingSystem.loggedInUser instanceof User) {
                        System.out.print("Enter flight number to book: ");
                        String flightNumberToBook = scanner.nextLine();
                        bookingSystem.bookFlight(flightNumberToBook);
                    } else {
                        System.out.println("You are not authorized to perform this operation.");
                    }
                    break;
                case 8:
                    if (bookingSystem.loggedInUser instanceof User) {
                        bookingSystem.viewBookings();
                    } else {
                        System.out.println("You are not authorized to perform this operation.");
                    }
                    break;
                case 9:
                    bookingSystem.logout();
                    break;
                case 10:
                    exit = true;
                    System.out.println("Exiting the application...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println();
        }

        scanner.close();
    }
}