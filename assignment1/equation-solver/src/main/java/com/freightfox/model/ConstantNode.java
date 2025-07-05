package com.freightfox.model;

import java.util.Map;

/**
 * Expression node representing a constant value.
 */
public class ConstantNode extends ExpressionNode {
    
    private final double value;
    
    public ConstantNode(double value) {
        this.value = value;
    }
    
    @Override
    public double evaluate(Map<String, Double> variables) {
        return value;
    }
    
    @Override
    public String toString() {
        return String.valueOf(value);
    }
    
    public double getValue() {
        return value;
    }
} 