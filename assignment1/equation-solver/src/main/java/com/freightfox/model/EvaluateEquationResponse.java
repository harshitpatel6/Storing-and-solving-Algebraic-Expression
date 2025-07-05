package com.freightfox.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/**
 * Response model for evaluating an equation.
 */
public class EvaluateEquationResponse {
    
    @JsonProperty("equationId")
    private Long equationId;
    
    @JsonProperty("equation")
    private String equation;
    
    @JsonProperty("variables")
    private Map<String, Double> variables;
    
    @JsonProperty("result")
    private Double result;
    
    public EvaluateEquationResponse() {}
    
    public EvaluateEquationResponse(Long equationId, String equation, Map<String, Double> variables, Double result) {
        this.equationId = equationId;
        this.equation = equation;
        this.variables = variables;
        this.result = result;
    }
    
    // Getters and Setters
    public Long getEquationId() {
        return equationId;
    }
    
    public void setEquationId(Long equationId) {
        this.equationId = equationId;
    }
    
    public String getEquation() {
        return equation;
    }
    
    public void setEquation(String equation) {
        this.equation = equation;
    }
    
    public Map<String, Double> getVariables() {
        return variables;
    }
    
    public void setVariables(Map<String, Double> variables) {
        this.variables = variables;
    }
    
    public Double getResult() {
        return result;
    }
    
    public void setResult(Double result) {
        this.result = result;
    }
    
    @Override
    public String toString() {
        return "EvaluateEquationResponse{" +
                "equationId=" + equationId +
                ", equation='" + equation + '\'' +
                ", variables=" + variables +
                ", result=" + result +
                '}';
    }
} 