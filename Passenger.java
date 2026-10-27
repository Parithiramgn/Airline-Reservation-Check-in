package model;

public class Passenger extends Person {

    private String passengerId;
    private String contact;

    public Passenger(String passengerId, String name, int age, String contact) {
        super(name, age);

        if (passengerId == null || passengerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Passenger ID cannot be empty.");
        }

        if (contact == null || contact.trim().isEmpty()) {
            throw new IllegalArgumentException("Contact cannot be empty.");
        }

        this.passengerId = passengerId.trim();
        this.contact = contact.trim();
    }

    public String getPassengerId() {
        return passengerId;
    }

    public String getContact() {
        return contact;
    }

    @Override
    public String getDetails() {
        return "Passenger ID: " + passengerId
                + ", Name: " + getName()
                + ", Age: " + getAge()
                + ", Contact: " + contact;
    }
}