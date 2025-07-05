package com.freightfox.exception;

/**
 * Exception thrown when an equation with the specified ID is not found.
 */
public class EquationNotFoundException extends RuntimeException {
    
    public EquationNotFoundException(String message) {
        super(message);
    }
    
    public EquationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
} 