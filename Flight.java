package model;

import java.util.ArrayList;
import java.util.List;

public class Flight {

    private String flightNumber;
    private String source;
    private String destination;
    private double fare;
    private Aircraft aircraft;
    private List<Seat> seats;

    public Flight(String flightNumber, String source,
                  String destination, double fare, Aircraft aircraft) {

        if (flightNumber == null || flightNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Flight number cannot be empty.");
        }

        if (source == null || source.trim().isEmpty()
                || destination == null || destination.trim().isEmpty()) {
            throw new IllegalArgumentException("Source and destination are required.");
        }

        if (source.trim().equalsIgnoreCase(destination.trim())) {
            throw new IllegalArgumentException("Source and destination must differ.");
        }

        if (!Double.isFinite(fare) || fare <= 0) {
            throw new IllegalArgumentException("Fare must be a positive number.");
        }

        if (aircraft == null) {
            throw new IllegalArgumentException("Aircraft is required.");
        }

        this.flightNumber = flightNumber.trim();
        this.source = source.trim();
        this.destination = destination.trim();
        this.fare = fare;
        this.aircraft = aircraft;

        seats = new ArrayList<>();

        for (int i = 1; i <= aircraft.getTotalSeats(); i++) {
            seats.add(new Seat(String.valueOf(i)));
        }
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public double getFare() {
        return fare;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public List<Seat> getSeats() {
        return new ArrayList<>(seats);
    }

    public Seat findSeat(String seatNumber) {
        for (Seat seat : seats) {
            if (seat.getSeatNumber().equalsIgnoreCase(seatNumber.trim())) {
                return seat;
            }
        }
        return null;
    }

    public int getAvailableSeats() {
        int count = 0;

        for (Seat seat : seats) {
            if (seat.isAvailable()) {
                count++;
            }
        }

        return count;
    }

    public void displayFlight() {
        System.out.println("--------------------------------------");
        System.out.println("Flight Number : " + flightNumber);
        System.out.println("Route         : " + source + " -> " + destination);
        System.out.println("Aircraft      : " + aircraft.getModel());
        System.out.printf("Fare per Seat : Rs. %.2f%n", fare);
        System.out.println("Total Seats   : " + aircraft.getTotalSeats());
        System.out.println("Available     : " + getAvailableSeats());
        System.out.println("Booked        : "
                + (aircraft.getTotalSeats() - getAvailableSeats()));
        System.out.println("--------------------------------------");
    }
}