package com.farming.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author naveen.tyagi
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource Not Found")
public class ResourceNotFoundException extends Exception {


    public ResourceNotFoundException(Exception e) {
        super(e);
    }
    public ResourceNotFoundException(String message) {
        super(message);
    }

}
