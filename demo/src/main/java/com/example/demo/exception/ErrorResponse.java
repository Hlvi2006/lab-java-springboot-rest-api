package com.example.demo.exception;

import java.util.List;

public class ErrorResponse {
    private int status;
    private String message;
    private String error;
    private List<String> details;

    public ErrorResponse(int status, String message, String error) {
        this.status = status;
        this.message = message;
        this.error = error;
    }

    public ErrorResponse(int status, String message, String error, List<String> details) {
        this.status = status;
        this.message = message;
        this.error = error;
        this.details = details;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    public List<String> getDetails() {
        return details;
    }
}
