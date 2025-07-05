package com.freightfox.exception;

/**
 * Exception thrown when an expression is invalid or cannot be parsed.
 */
public class InvalidExpressionException extends RuntimeException {
    
    public InvalidExpressionException(String message) {
        super(message);
    }
    
    public InvalidExpressionException(String message, Throwable cause) {
        super(message, cause);
    }
} 