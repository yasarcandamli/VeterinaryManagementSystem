package dev.patika.VeterinaryManagementSystem.core.exception;

public class AppointmentConflictException extends RuntimeException{
    public AppointmentConflictException(String message) {
        super(message);
    }
}
