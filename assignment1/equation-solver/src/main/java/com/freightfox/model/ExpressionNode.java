package com.freightfox.model;

import java.util.Map;

/**
 * Abstract base class for expression tree nodes.
 */
public abstract class ExpressionNode {
    
    /**
     * Evaluates the expression node with given variable values.
     * 
     * @param variables Map of variable names to their values
     * @return The result of the evaluation
     */
    public abstract double evaluate(Map<String, Double> variables);
    
    /**
     * Returns a string representation of the expression.
     * 
     * @return String representation
     */
    public abstract String toString();
} 