package com.freightfox.model;

import java.util.Map;

/**
 * Expression node representing an operator with left and right operands.
 */
public class OperatorNode extends ExpressionNode {
    
    private final String operator;
    private final ExpressionNode left;
    private final ExpressionNode right;
    
    public OperatorNode(String operator, ExpressionNode left, ExpressionNode right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }
    
    @Override
    public double evaluate(Map<String, Double> variables) {
        double leftValue = left.evaluate(variables);
        double rightValue = right.evaluate(variables);
        
        return switch (operator) {
            case "+" -> leftValue + rightValue;
            case "-" -> leftValue - rightValue;
            case "*" -> leftValue * rightValue;
            case "/" -> {
                if (rightValue == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                yield leftValue / rightValue;
            }
            case "^" -> Math.pow(leftValue, rightValue);
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
    }
    
    @Override
    public String toString() {
        return "(" + left.toString() + " " + operator + " " + right.toString() + ")";
    }
    
    public String getOperator() {
        return operator;
    }
    
    public ExpressionNode getLeft() {
        return left;
    }
    
    public ExpressionNode getRight() {
        return right;
    }
} 