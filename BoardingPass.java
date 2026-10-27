package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BoardingPass {

    private String boardingPassId;
    private Passenger passenger;
    private Flight flight;
    private Seat seat;
    private String gate;
    private LocalDateTime checkInTime;

    public BoardingPass(String boardingPassId, Passenger passenger,
                        Flight flight, Seat seat, String gate) {

        if (boardingPassId == null || boardingPassId.trim().isEmpty()) {
            throw new IllegalArgumentException("Boarding pass ID is required.");
        }

        if (passenger == null || flight == null || seat == null) {
            throw new IllegalArgumentException(
                    "Passenger, flight and seat are required.");
        }

        if (gate == null || gate.trim().isEmpty()) {
            throw new IllegalArgumentException("Gate cannot be empty.");
        }

        this.boardingPassId = boardingPassId;
        this.passenger = passenger;
        this.flight = flight;
        this.seat = seat;
        this.gate = gate;
        this.checkInTime = LocalDateTime.now();
    }

    public void displayBoardingPass() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        System.out.println("\n========== BOARDING PASS ==========");
        System.out.println("Boarding Pass ID : " + boardingPassId);
        System.out.println("Passenger       : " + passenger.getName());
        System.out.println("Passenger ID    : " + passenger.getPassengerId());
        System.out.println("Flight Number   : " + flight.getFlightNumber());
        System.out.println("Route           : " + flight.getSource()
                + " -> " + flight.getDestination());
        System.out.println("Seat Number     : " + seat.getSeatNumber());
        System.out.println("Gate            : " + gate);
        System.out.println("Check-in Time   : "
                + checkInTime.format(formatter));
        System.out.println("===================================");
    }
}