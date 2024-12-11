package com.epam.exceptions;

public class UserNotAllowedToLoanException extends RuntimeException {
    public UserNotAllowedToLoanException(String message) {
        super(message);
    }
}
