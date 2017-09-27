package com.farming.app.exception;

import com.farming.app.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(BadRequestException e){

        ErrorResponse errorResponse = new ErrorResponse(400, "Bad Request");
        return  new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrganisationNotFoundException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(OrganisationNotFoundException e){

        ErrorResponse errorResponse = new ErrorResponse(404, "Organisation Not Found");
        return  new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(ClientNotFoundException e){

        ErrorResponse errorResponse = new ErrorResponse(404, "Client Not Found");
        return  new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FarmNotFoundException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(FarmNotFoundException e){

        ErrorResponse errorResponse = new ErrorResponse(404, "Farm Not Found");
        return  new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FieldNotFoundException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(FieldNotFoundException e){

        ErrorResponse errorResponse = new ErrorResponse(404, "Field Not Found");
        return  new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(ResourceNotFoundException e){

        ErrorResponse errorResponse = new ErrorResponse(404, "Resource Not Found");
        return  new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NullPointerException.class, Exception.class})
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception e){

        ErrorResponse errorResponse = new ErrorResponse(500, "Server Error");
        return  new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}