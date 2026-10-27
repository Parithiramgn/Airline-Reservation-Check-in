package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import service.AirlineService;
import exception.InvalidBookingException;

public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static AirlineService service = new AirlineService();

    public static void main(String[] args) {

        boolean running = true;

        System.out.println("==============================================");
        System.out.println(" AIRLINE RESERVATION & CHECK-IN SYSTEM");
        System.out.println("==============================================");

        while (running) {

            displayMenu();

            try {
                int choice = readInt("Enter your choice: ");

                switch (choice) {

                    case 1:
                        addFlight();
                        break;

                    case 2:
                        addPassenger();
                        break;

                    case 3:
                        createBooking();
                        break;

                    case 4:
                        makePayment();
                        break;

                    case 5:
                        checkIn();
                        break;

                    case 6:
                        cancelBooking();
                        break;

                    case 7:
                        service.displayFlights();
                        break;

                    case 8:
                        running = false;
                        System.out.println(
                                "\nThank you for using the Airline Reservation System.");
                        break;

                    default:
                        System.out.println(
                                "Invalid menu choice. Enter a number from 1 to 8.");
                }

            } catch (InvalidBookingException e) {
                System.out.println("Booking Error: " + e.getMessage());

            } catch (IllegalArgumentException e) {
                System.out.println("Input Error: " + e.getMessage());

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        sc.close();
    }

    private static void displayMenu() {

        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. Add Flight");
        System.out.println("2. Add Passenger");
        System.out.println("3. Create Booking");
        System.out.println("4. Make Payment");
        System.out.println("5. Check-in & Issue Boarding Pass");
        System.out.println("6. Cancel Booking");
        System.out.println("7. Display Flights");
        System.out.println("8. Exit");
        System.out.println("===============================");
    }

    private static int readInt(String message) {

        while (true) {
            System.out.print(message);

            String input = sc.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    private static double readDouble(String message) {

        while (true) {
            System.out.print(message);

            String input = sc.nextLine().trim();

            try {
                double value = Double.parseDouble(input);

                if (!Double.isFinite(value)) {
                    System.out.println("Enter a finite numeric value.");
                    continue;
                }

                return value;

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static String readText(String message) {

        while (true) {
            System.out.print(message);

            String input = sc.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("This field cannot be empty.");
        }
    }

    private static void addFlight() {

        System.out.println("\n========== ADD FLIGHT ==========");

        String flightNumber = readText("Enter flight number: ");
        String source = readText("Enter source: ");
        String destination = readText("Enter destination: ");

        int totalSeats;

        while (true) {
            totalSeats = readInt("Enter total seats (1-200): ");

            if (totalSeats >= 1 && totalSeats <= 200) {
                break;
            }

            System.out.println("Seat capacity must be between 1 and 200.");
        }

        double fare;

        while (true) {
            fare = readDouble("Enter fare per seat: ");

            if (fare > 0) {
                break;
            }

            System.out.println("Fare must be greater than zero.");
        }

        service.addFlight(
                flightNumber, source, destination, totalSeats, fare);
    }

    private static void addPassenger() {

        System.out.println("\n========== ADD PASSENGER ==========");

        String name = readText("Enter passenger name: ");

        int age;

        while (true) {
            age = readInt("Enter age: ");

            if (age >= 1 && age <= 120) {
                break;
            }

            System.out.println("Age must be between 1 and 120.");
        }

        String contact = readText("Enter contact number: ");

        service.addPassenger(name, age, contact);
    }

    private static void createBooking()
            throws InvalidBookingException {

        System.out.println("\n========== CREATE BOOKING ==========");

        String flightNumber = readText("Enter flight number: ");
        String passengerId = readText("Enter passenger ID: ");

        int numberOfSeats;

        while (true) {
            numberOfSeats = readInt("Enter number of seats to book: ");

            if (numberOfSeats > 0) {
                break;
            }

            System.out.println("Number of seats must be positive.");
        }

        List<String> seats = new ArrayList<>();

        for (int i = 1; i <= numberOfSeats; i++) {
            seats.add(readText("Enter seat number " + i + ": "));
        }

        service.createBooking(flightNumber, passengerId, seats);
    }

    private static void makePayment()
            throws InvalidBookingException {

        System.out.println("\n========== MAKE PAYMENT ==========");

        String bookingId = readText("Enter booking ID: ");

        service.makePayment(bookingId);
    }

    private static void checkIn()
            throws InvalidBookingException {

        System.out.println("\n========== CHECK-IN ==========");

        String bookingId = readText("Enter booking ID: ");

        service.checkIn(bookingId);
    }

    private static void cancelBooking()
            throws InvalidBookingException {

        System.out.println("\n========== CANCEL BOOKING ==========");

        String bookingId = readText("Enter booking ID: ");

        service.cancelBooking(bookingId);
    }
}