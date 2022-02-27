package hu.dianaszanto.trackinvoices.service;

import hu.dianaszanto.trackinvoices.model.exception.NoSuchInvoiceExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoSuchInvoiceExistException.class)
    public ResponseEntity<Object> handleNoSuchInvoiceExistException(NoSuchInvoiceExistException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(e.getMessage(), status);
    }
}
