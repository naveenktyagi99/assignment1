package com.farming.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {

    @JsonProperty("errorCode")
    private int errorCode;

    @JsonProperty("message")
    private String message;

    public ErrorResponse(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
