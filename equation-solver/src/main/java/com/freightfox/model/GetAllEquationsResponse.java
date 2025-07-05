package com.freightfox.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Response model for getting all equations.
 * Matches the expected API format from the assignment.
 */
public class GetAllEquationsResponse {
    
    @JsonProperty("equations")
    private List<EquationResponse> equations;
    
    public GetAllEquationsResponse() {}
    
    public GetAllEquationsResponse(List<EquationResponse> equations) {
        this.equations = equations;
    }
    
    // Getters and Setters
    public List<EquationResponse> getEquations() {
        return equations;
    }
    
    public void setEquations(List<EquationResponse> equations) {
        this.equations = equations;
    }
    
    /**
     * Inner class for individual equation response.
     */
    public static class EquationResponse {
        
        @JsonProperty("equationId")
        private String equationId;
        
        @JsonProperty("equation")
        private String equation;
        
        public EquationResponse() {}
        
        public EquationResponse(String equationId, String equation) {
            this.equationId = equationId;
            this.equation = equation;
        }
        
        // Getters and Setters
        public String getEquationId() {
            return equationId;
        }
        
        public void setEquationId(String equationId) {
            this.equationId = equationId;
        }
        
        public String getEquation() {
            return equation;
        }
        
        public void setEquation(String equation) {
            this.equation = equation;
        }
    }
} 