package dev.ia.exceptions;

public class BookingNotFoundExceptions extends RuntimeException {
    public BookingNotFoundExceptions(String message) {
        super(message);
    }
}
