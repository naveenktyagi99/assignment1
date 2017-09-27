package com.farming.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author naveen.tyagi
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Farm Not Found")
public class FarmNotFoundException extends ResourceNotFoundException {

    public FarmNotFoundException(String message) {
        super(message);
    }
}
