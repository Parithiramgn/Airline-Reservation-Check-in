package model;

import java.time.LocalDateTime;

public class Payment {

    private String paymentId;
    private Booking booking;
    private double amount;
    private String status;
    private LocalDateTime paymentTime;

    public Payment(String paymentId, Booking booking) {
        if (paymentId == null || paymentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Payment ID cannot be empty.");
        }

        if (booking == null) {
            throw new IllegalArgumentException("Booking is required.");
        }

        this.paymentId = paymentId;
        this.booking = booking;
        this.amount = booking.getTotalAmount();
        this.status = "PENDING";
    }

    public String getPaymentId() {
        return paymentId;
    }

    public Booking getBooking() {
        return booking;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public void confirmPayment() {
        if (!booking.getStatus().equals("CONFIRMED")) {
            throw new IllegalStateException(
                    "Payment cannot be completed for this booking.");
        }

        if (status.equals("SUCCESS")) {
            throw new IllegalStateException("Payment is already completed.");
        }

        status = "SUCCESS";
        paymentTime = LocalDateTime.now();
    }

    public void displayPayment() {
        System.out.println("\n========== PAYMENT DETAILS ==========");
        System.out.println("Payment ID : " + paymentId);
        System.out.println("Booking ID : " + booking.getBookingId());
        System.out.printf("Amount     : Rs. %.2f%n", amount);
        System.out.println("Status     : " + status);

        if (paymentTime != null) {
            System.out.println("Time       : " + paymentTime);
        }

        System.out.println("=====================================");
    }
}