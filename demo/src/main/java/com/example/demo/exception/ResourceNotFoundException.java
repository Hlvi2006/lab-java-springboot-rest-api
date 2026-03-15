package com.example.demo.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message,Long id) {
        super(message+" can not find with id:"+id);
    }
}
