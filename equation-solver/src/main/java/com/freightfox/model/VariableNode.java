package com.freightfox.model;

import java.util.Map;

/**
 * Expression node representing a variable.
 */
public class VariableNode extends ExpressionNode {
    
    private final String variableName;
    
    public VariableNode(String variableName) {
        this.variableName = variableName;
    }
    
    @Override
    public double evaluate(Map<String, Double> variables) {
        if (!variables.containsKey(variableName)) {
            throw new IllegalArgumentException("Variable '" + variableName + "' not found in provided values");
        }
        return variables.get(variableName);
    }
    
    @Override
    public String toString() {
        return variableName;
    }
    
    public String getVariableName() {
        return variableName;
    }
} 