package dev.patika.VeterinaryManagementSystem.core.config;

import dev.patika.VeterinaryManagementSystem.core.exception.*;
import dev.patika.VeterinaryManagementSystem.core.result.Result;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.core.utility.ResultHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Result> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(ResultHelper.notFoundError(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VaccineConflictException.class)
    public ResponseEntity<Result> handleConflictException(VaccineConflictException e) {
        return new ResponseEntity<>(ResultHelper.vaccineConflictError(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RecordAlreadyExistException.class)
    public ResponseEntity<Result> handleRecordAlreadyExistException(RecordAlreadyExistException e) {
        return new ResponseEntity<>(ResultHelper.recordAlreadyExistError(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DoctorNotAvailableException.class)
    public ResponseEntity<Result> handleDoctorNotAvailableException(DoctorNotAvailableException e) {
        return new ResponseEntity<>(ResultHelper.doctorNotAvailableError(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AppointmentConflictException.class)
    public ResponseEntity<Result> handleAppointmentConflictException(AppointmentConflictException e) {
        return new ResponseEntity<>(ResultHelper.appointmentConflictError(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AppointmentHourException.class)
    public ResponseEntity<Result> handleAppointmentHourException(AppointmentHourException e) {
        return new ResponseEntity<>(ResultHelper.appointmentHourError(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultData<List<String>>> handleValidationErrors(MethodArgumentNotValidException e) {
        List<String> validationErrorList = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        return new ResponseEntity<>(ResultHelper.validateError(validationErrorList), HttpStatus.BAD_REQUEST);
    }
}
