package dev.patika.VeterinaryManagementSystem.core.exception;

public class RecordAlreadyExistException extends RuntimeException {
    public RecordAlreadyExistException(String message) {
        super(message);
    }
}
