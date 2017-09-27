package com.farming.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author naveen.tyagi
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Client Not Found")
public class ClientNotFoundException extends ResourceNotFoundException {

    public ClientNotFoundException(String message) {
        super(message);
    }
}
