package com.freightfox.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response model for storing an equation.
 */
public class StoreEquationResponse {
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("equationId")
    private Long equationId;
    
    public StoreEquationResponse() {}
    
    public StoreEquationResponse(String message, Long equationId) {
        this.message = message;
        this.equationId = equationId;
    }
    
    // Getters and Setters
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Long getEquationId() {
        return equationId;
    }
    
    public void setEquationId(Long equationId) {
        this.equationId = equationId;
    }
    
    @Override
    public String toString() {
        return "StoreEquationResponse{" +
                "message='" + message + '\'' +
                ", equationId=" + equationId +
                '}';
    }
} 