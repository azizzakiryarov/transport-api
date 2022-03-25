package se.knowit.iz.exception;

public class NotFoundVehicleException extends Exception {
    public NotFoundVehicleException(String message) {
        super(message);
    }

    public NotFoundVehicleException() {
        super();
    }
}