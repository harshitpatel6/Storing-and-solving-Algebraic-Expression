package com.freightfox.service;

import com.freightfox.model.*;
import java.util.List;
import java.util.Map;

/**
 * Service interface for equation operations.
 */
public interface EquationService {
    
    /**
     * Stores an equation and returns its ID.
     * 
     * @param equation The equation string to store
     * @return The ID of the stored equation
     * @throws InvalidExpressionException if the equation is invalid
     */
    Long storeEquation(String equation);
    
    /**
     * Retrieves all stored equations.
     * 
     * @return List of all stored equations
     */
    List<Equation> getAllEquations();
    
    /**
     * Retrieves an equation by its ID.
     * 
     * @param id The equation ID
     * @return The equation
     * @throws EquationNotFoundException if the equation is not found
     */
    Equation getEquationById(Long id);
    
    /**
     * Evaluates an equation with given variable values.
     * 
     * @param id The equation ID
     * @param variables Map of variable names to their values
     * @return The evaluation result
     * @throws EquationNotFoundException if the equation is not found
     * @throws InvalidExpressionException if the expression cannot be evaluated
     */
    double evaluateEquation(Long id, Map<String, Double> variables);
} 