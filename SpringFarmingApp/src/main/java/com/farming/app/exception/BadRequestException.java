package com.farming.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author naveen.tyagi
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad Request")
public class BadRequestException extends Exception {


    public BadRequestException(Throwable e) {
        super(e);
    }

    public BadRequestException(String message) {
        super(message);
    }
}