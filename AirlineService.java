package service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Aircraft;
import model.BoardingPass;
import model.Booking;
import model.Flight;
import model.Passenger;
import model.Payment;
import model.Seat;
import exception.InvalidBookingException;

public class AirlineService {

    private List<Flight> flights = new ArrayList<Flight>();
    private List<Passenger> passengers = new ArrayList<Passenger>();
    private List<Booking> bookings = new ArrayList<Booking>();
    private List<Payment> payments = new ArrayList<Payment>();
    private List<BoardingPass> boardingPasses =
            new ArrayList<BoardingPass>();

    private Set<String> checkedInBookings = new HashSet<String>();

    private int passengerCounter = 1;
    private int bookingCounter = 1;
    private int paymentCounter = 1;
    private int boardingPassCounter = 1;
    private int aircraftCounter = 1;

    public void addFlight(String flightNumber, String source,
            String destination, int totalSeats, double fare) {

        for (Flight flight : flights) {
            if (flight.getFlightNumber().equalsIgnoreCase(flightNumber)) {
                throw new IllegalArgumentException(
                        "Flight number already exists.");
            }
        }

        Aircraft aircraft = new Aircraft(
                "AC" + aircraftCounter,
                "Aircraft-" + aircraftCounter,
                totalSeats);

        aircraftCounter++;

        Flight flight = new Flight(
                flightNumber, source, destination, fare, aircraft);

        flights.add(flight);

        System.out.println("Flight added successfully.");
        flight.displayFlight();
    }

    public void addPassenger(String name, int age, String contact) {

        Passenger passenger = new Passenger(
                "P" + passengerCounter, name, age, contact);

        passengers.add(passenger);
        passengerCounter++;

        System.out.println("Passenger added successfully.");
        System.out.println(passenger.getDetails());
    }

    public Flight findFlight(String flightNumber) {

        for (Flight flight : flights) {
            if (flight.getFlightNumber()
                    .equalsIgnoreCase(flightNumber)) {
                return flight;
            }
        }

        return null;
    }

    public Passenger findPassenger(String passengerId) {

        for (Passenger passenger : passengers) {
            if (passenger.getPassengerId()
                    .equalsIgnoreCase(passengerId)) {
                return passenger;
            }
        }

        return null;
    }

    public Booking findBooking(String bookingId) {

        for (Booking booking : bookings) {
            if (booking.getBookingId()
                    .equalsIgnoreCase(bookingId)) {
                return booking;
            }
        }

        return null;
    }

    private Payment findPayment(String bookingId) {

        for (Payment payment : payments) {
            if (payment.getBooking().getBookingId()
                    .equalsIgnoreCase(bookingId)) {
                return payment;
            }
        }

        return null;
    }

    public void createBooking(String flightNumber, String passengerId,
            List<String> requestedSeats)
            throws InvalidBookingException {

        Flight flight = findFlight(flightNumber);
        Passenger passenger = findPassenger(passengerId);

        if (flight == null) {
            throw new InvalidBookingException("Flight not found.");
        }

        if (passenger == null) {
            throw new InvalidBookingException("Passenger not found.");
        }

        if (requestedSeats == null || requestedSeats.isEmpty()) {
            throw new InvalidBookingException(
                    "Select at least one seat.");
        }

        Set<String> uniqueSeats = new HashSet<String>();
        List<Seat> selectedSeats = new ArrayList<Seat>();

        for (String seatNumber : requestedSeats) {

            String number = seatNumber.trim();

            if (!uniqueSeats.add(number.toUpperCase())) {
                throw new InvalidBookingException(
                        "Duplicate seat selected: " + number);
            }

            Seat seat = flight.findSeat(number);

            if (seat == null) {
                throw new InvalidBookingException(
                        "Invalid seat number: " + number);
            }

            if (!seat.isAvailable()) {
                throw new InvalidBookingException(
                        "Seat " + number + " is already booked.");
            }

            selectedSeats.add(seat);
        }

        Booking booking = new Booking(
                "B" + bookingCounter,
                flight,
                passenger,
                selectedSeats);

        for (Seat seat : selectedSeats) {
            seat.setAvailable(false);
        }

        bookings.add(booking);
        bookingCounter++;

        booking.displayReceipt();
        flight.displayFlight();
    }

    public void makePayment(String bookingId)
            throws InvalidBookingException {

        Booking booking = findBooking(bookingId);

        if (booking == null) {
            throw new InvalidBookingException("Booking not found.");
        }

        if (!booking.getStatus().equals("CONFIRMED")) {
            throw new InvalidBookingException(
                    "Booking is not eligible for payment.");
        }

        if (findPayment(bookingId) != null) {
            throw new InvalidBookingException(
                    "Payment has already been recorded.");
        }

        Payment payment = new Payment(
                "PAY" + paymentCounter, booking);

        payment.confirmPayment();

        payments.add(payment);
        paymentCounter++;

        payment.displayPayment();
    }

    public void checkIn(String bookingId)
            throws InvalidBookingException {

        Booking booking = findBooking(bookingId);

        if (booking == null) {
            throw new InvalidBookingException("Booking not found.");
        }

        if (!booking.getStatus().equals("CONFIRMED")) {
            throw new InvalidBookingException(
                    "Booking is not eligible for check-in.");
        }

        Payment payment = findPayment(bookingId);

        if (payment == null || !payment.getStatus().equals("SUCCESS")) {
            throw new InvalidBookingException(
                    "Payment must be completed before check-in.");
        }

        if (checkedInBookings.contains(bookingId.toUpperCase())) {
            throw new InvalidBookingException(
                    "Booking has already been checked in.");
        }

        for (Seat seat : booking.getBookedSeats()) {

            BoardingPass pass = new BoardingPass(
                    "BP" + boardingPassCounter,
                    booking.getPassenger(),
                    booking.getFlight(),
                    seat,
                    "A" + ((boardingPassCounter - 1) % 5 + 1));

            boardingPasses.add(pass);
            boardingPassCounter++;

            pass.displayBoardingPass();
        }

        checkedInBookings.add(bookingId.toUpperCase());
        booking.setStatus("CHECKED_IN");

        System.out.println("Check-in completed successfully.");
    }

    public void cancelBooking(String bookingId)
            throws InvalidBookingException {

        Booking booking = findBooking(bookingId);

        if (booking == null) {
            throw new InvalidBookingException("Booking not found.");
        }

        if (booking.getStatus().equals("CANCELLED")) {
            throw new InvalidBookingException(
                    "Booking is already cancelled.");
        }

        if (booking.getStatus().equals("CHECKED_IN")) {
            throw new InvalidBookingException(
                    "Checked-in bookings cannot be cancelled.");
        }

        for (Seat seat : booking.getBookedSeats()) {
            seat.setAvailable(true);
        }

        booking.setStatus("CANCELLED");

        Payment payment = findPayment(bookingId);

        if (payment != null && payment.getStatus().equals("SUCCESS")) {
            System.out.println(
                    "Note: Automatic refunds are not implemented.");
        }

        System.out.println("Booking cancelled successfully.");
        System.out.println("Seats have been released.");

        booking.getFlight().displayFlight();
    }

    public void displayFlights() {

        if (flights.isEmpty()) {
            System.out.println("No flights available.");
            return;
        }

        for (Flight flight : flights) {
            flight.displayFlight();
        }
    }
}