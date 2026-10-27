package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Booking {

    private String bookingId;
    private Flight flight;
    private Passenger passenger;
    private List<Seat> bookedSeats;
    private double baseFare;
    private double taxes;
    private double totalAmount;
    private String status;

    public Booking(String bookingId, Flight flight,
                   Passenger passenger, List<Seat> seats) {

        if (bookingId == null || bookingId.trim().isEmpty()) {
            throw new IllegalArgumentException("Booking ID cannot be empty.");
        }

        if (flight == null || passenger == null) {
            throw new IllegalArgumentException("Flight and passenger are required.");
        }

        if (seats == null || seats.isEmpty()) {
            throw new IllegalArgumentException("At least one seat is required.");
        }

        this.bookingId = bookingId;
        this.flight = flight;
        this.passenger = passenger;
        this.bookedSeats = new ArrayList<>(seats);

        baseFare = flight.getFare() * seats.size();
        taxes = baseFare * 0.10;
        totalAmount = baseFare + taxes;
        status = "CONFIRMED";
    }

    public String getBookingId() {
        return bookingId;
    }

    public Flight getFlight() {
        return flight;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public List<Seat> getBookedSeats() {
        return Collections.unmodifiableList(bookedSeats);
    }

    public double getBaseFare() {
        return baseFare;
    }

    public double getTaxes() {
        return taxes;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void displayReceipt() {
        System.out.println("\n========== BOOKING RECEIPT ==========");
        System.out.println("Booking ID     : " + bookingId);
        System.out.println("Passenger      : " + passenger.getName());
        System.out.println("Flight Number  : " + flight.getFlightNumber());
        System.out.println("Route          : " + flight.getSource()
                + " -> " + flight.getDestination());

        System.out.print("Seats          : ");

        for (int i = 0; i < bookedSeats.size(); i++) {
            System.out.print(bookedSeats.get(i).getSeatNumber());

            if (i < bookedSeats.size() - 1) {
                System.out.print(", ");
            }
        }

        System.out.printf("%nBase Fare      : Rs. %.2f%n", baseFare);
        System.out.printf("Taxes          : Rs. %.2f%n", taxes);
        System.out.printf("Total Amount   : Rs. %.2f%n", totalAmount);
        System.out.println("Booking Status : " + status);
        System.out.println("=====================================");
    }
}