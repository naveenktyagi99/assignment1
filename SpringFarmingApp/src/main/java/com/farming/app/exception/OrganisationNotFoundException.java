package com.farming.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author naveen.tyagi
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Organisation Not Found")
public class OrganisationNotFoundException extends ResourceNotFoundException {

    public OrganisationNotFoundException(String message) {
        super(message);
    }

}
