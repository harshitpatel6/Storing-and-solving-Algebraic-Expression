package com.freightfox.model;

import jakarta.validation.constraints.NotEmpty;
import java.util.Map;

/**
 * Request model for evaluating an equation with variable values.
 */
public class EvaluateEquationRequest {
    
    @NotEmpty(message = "Variables map cannot be empty")
    private Map<String, Double> variables;
    
    public EvaluateEquationRequest() {}
    
    public EvaluateEquationRequest(Map<String, Double> variables) {
        this.variables = variables;
    }
    
    // Getters and Setters
    public Map<String, Double> getVariables() {
        return variables;
    }
    
    public void setVariables(Map<String, Double> variables) {
        this.variables = variables;
    }
    
    @Override
    public String toString() {
        return "EvaluateEquationRequest{" +
                "variables=" + variables +
                '}';
    }
} 