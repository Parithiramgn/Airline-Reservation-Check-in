package model;

public class Aircraft {

    private String aircraftId;
    private String model;
    private int totalSeats;

    public Aircraft(String aircraftId, String model, int totalSeats) {
        if (aircraftId == null || aircraftId.trim().isEmpty()) {
            throw new IllegalArgumentException("Aircraft ID cannot be empty.");
        }

        if (model == null || model.trim().isEmpty()) {
            throw new IllegalArgumentException("Aircraft model cannot be empty.");
        }

        if (totalSeats <= 0 || totalSeats > 200) {
            throw new IllegalArgumentException("Seats must be between 1 and 200.");
        }

        this.aircraftId = aircraftId.trim();
        this.model = model.trim();
        this.totalSeats = totalSeats;
    }

    public String getAircraftId() {
        return aircraftId;
    }

    public String getModel() {
        return model;
    }

    public int getTotalSeats() {
        return totalSeats;
    }
}