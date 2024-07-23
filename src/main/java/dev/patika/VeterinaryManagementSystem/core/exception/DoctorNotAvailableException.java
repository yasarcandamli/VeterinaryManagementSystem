package dev.patika.VeterinaryManagementSystem.core.exception;

public class DoctorNotAvailableException extends RuntimeException{
    public DoctorNotAvailableException(String message) {
        super(message);
    }
}
