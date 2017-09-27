package com.farming.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author naveen.tyagi
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Field Not Found")
public class FieldNotFoundException extends ResourceNotFoundException {
    
    public FieldNotFoundException(String message) {
        super(message);
    }
    public FieldNotFoundException(Exception exception) {
        super(exception);
    }
}
