package model;

public class Seat {

    private String seatNumber;
    private boolean available;

    public Seat(String seatNumber) {
        if (seatNumber == null || seatNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Seat number cannot be empty.");
        }

        this.seatNumber = seatNumber;
        this.available = true;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}