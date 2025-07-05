package com.freightfox.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class representing a stored equation.
 */
public class Equation {
    
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("equation")
    private String equation;
    
    public Equation() {}
    
    public Equation(Long id, String equation) {
        this.id = id;
        this.equation = equation;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getEquation() {
        return equation;
    }
    
    public void setEquation(String equation) {
        this.equation = equation;
    }
    
    @Override
    public String toString() {
        return "Equation{" +
                "id=" + id +
                ", equation='" + equation + '\'' +
                '}';
    }
} 