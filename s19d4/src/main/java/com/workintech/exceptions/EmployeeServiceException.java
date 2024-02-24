package com.workintech.exceptions;

public class EmployeeServiceException extends RuntimeException{
    public EmployeeServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
