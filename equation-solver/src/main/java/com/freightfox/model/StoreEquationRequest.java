package com.freightfox.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Request model for storing an equation.
 */
public class StoreEquationRequest {
    
    @NotBlank(message = "Equation cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\+\\-\\*\\/\\(\\)\\^]+$", 
             message = "Equation contains invalid characters. Only letters, numbers, spaces, and operators (+, -, *, /, ^, ()) are allowed.")
    private String equation;
    
    public StoreEquationRequest() {}
    
    public StoreEquationRequest(String equation) {
        this.equation = equation;
    }
    
    // Getters and Setters
    public String getEquation() {
        return equation;
    }
    
    public void setEquation(String equation) {
        this.equation = equation;
    }
    
    @Override
    public String toString() {
        return "StoreEquationRequest{" +
                "equation='" + equation + '\'' +
                '}';
    }
} 