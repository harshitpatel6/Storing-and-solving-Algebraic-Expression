package com.freightfox.util;

import com.freightfox.model.*;
import java.util.*;

/**
 * Utility class for parsing algebraic expressions using the Shunting Yard algorithm.
 * Converts infix expressions to postfix notation and builds expression trees.
 */
public class ExpressionParser {
    
    private static final Map<String, Integer> OPERATOR_PRECEDENCE = Map.of(
        "+", 1,
        "-", 1,
        "*", 2,
        "/", 2,
        "^", 3
    );
    
    /**
     * Parses an infix expression and builds an expression tree.
     * 
     * @param expression The infix expression string
     * @return The root node of the expression tree
     * @throws IllegalArgumentException if the expression is invalid
     */
    public static ExpressionNode parseExpression(String expression) {
        if (expression == null || expression.trim().isEmpty()) {
            throw new IllegalArgumentException("Expression cannot be null or empty");
        }
        
        String[] tokens = tokenize(expression);
        String[] postfix = infixToPostfix(tokens);
        return buildExpressionTree(postfix);
    }
    
    /**
     * Tokenizes the expression string into individual tokens.
     * 
     * @param expression The expression string
     * @return Array of tokens
     */
    private static String[] tokenize(String expression) {
        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();
        boolean inNumber = false;
        
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            
            if (Character.isWhitespace(c)) {
                // Skip whitespace
                continue;
            } else if (isOperator(String.valueOf(c)) || c == '(' || c == ')') {
                // Flush current token if exists
                if (currentToken.length() > 0) {
                    tokens.add(currentToken.toString());
                    currentToken.setLength(0);
                    inNumber = false;
                }
                tokens.add(String.valueOf(c));
            } else if (Character.isDigit(c) || c == '.') {
                // Number part
                if (!inNumber && currentToken.length() > 0 && Character.isLetter(currentToken.charAt(0))) {
                    // We were building a variable, now we have a number - flush and add *
                    tokens.add(currentToken.toString());
                    tokens.add("*");
                    currentToken.setLength(0);
                }
                currentToken.append(c);
                inNumber = true;
            } else if (Character.isLetter(c)) {
                // Variable part
                if (inNumber && currentToken.length() > 0) {
                    // We were building a number, now we have a letter - flush and add *
                    tokens.add(currentToken.toString());
                    tokens.add("*");
                    currentToken.setLength(0);
                }
                currentToken.append(c);
                inNumber = false;
            } else {
                throw new IllegalArgumentException("Invalid character in expression: " + c);
            }
        }
        
        // Add any remaining token
        if (currentToken.length() > 0) {
            tokens.add(currentToken.toString());
        }
        
        return tokens.toArray(new String[0]);
    }
    

    
    /**
     * Converts infix expression to postfix notation using Shunting Yard algorithm.
     * 
     * @param tokens Array of infix tokens
     * @return Array of postfix tokens
     */
    private static String[] infixToPostfix(String[] tokens) {
        List<String> output = new ArrayList<>();
        Stack<String> operators = new Stack<>();
        
        for (String token : tokens) {
            if (isNumber(token)) {
                output.add(token);
            } else if (isVariable(token)) {
                output.add(token);
            } else if (isOperator(token)) {
                while (!operators.isEmpty() && !operators.peek().equals("(") &&
                       getPrecedence(operators.peek()) >= getPrecedence(token)) {
                    output.add(operators.pop());
                }
                operators.push(token);
            } else if (token.equals("(")) {
                operators.push(token);
            } else if (token.equals(")")) {
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    output.add(operators.pop());
                }
                if (!operators.isEmpty() && operators.peek().equals("(")) {
                    operators.pop(); // Remove the opening parenthesis
                } else {
                    throw new IllegalArgumentException("Mismatched parentheses");
                }
            }
        }
        
        // Pop remaining operators
        while (!operators.isEmpty()) {
            String op = operators.pop();
            if (op.equals("(")) {
                throw new IllegalArgumentException("Mismatched parentheses");
            }
            output.add(op);
        }
        
        return output.toArray(new String[0]);
    }
    
    /**
     * Builds an expression tree from postfix notation.
     * 
     * @param postfix Array of postfix tokens
     * @return Root node of the expression tree
     */
    private static ExpressionNode buildExpressionTree(String[] postfix) {
        Stack<ExpressionNode> stack = new Stack<>();
        
        for (String token : postfix) {
            if (isNumber(token)) {
                stack.push(new ConstantNode(Double.parseDouble(token)));
            } else if (isVariable(token)) {
                stack.push(new VariableNode(token));
            } else if (isOperator(token)) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Invalid expression: insufficient operands for operator " + token);
                }
                ExpressionNode right = stack.pop();
                ExpressionNode left = stack.pop();
                stack.push(new OperatorNode(token, left, right));
            }
        }
        
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid expression: too many operands");
        }
        
        return stack.pop();
    }
    
    /**
     * Checks if a token is a number.
     * 
     * @param token The token to check
     * @return true if the token is a number
     */
    private static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Checks if a token is a variable (letter).
     * 
     * @param token The token to check
     * @return true if the token is a variable
     */
    private static boolean isVariable(String token) {
        return token.length() == 1 && Character.isLetter(token.charAt(0));
    }
    
    /**
     * Checks if a token is an operator.
     * 
     * @param token The token to check
     * @return true if the token is an operator
     */
    private static boolean isOperator(String token) {
        return OPERATOR_PRECEDENCE.containsKey(token);
    }
    
    /**
     * Gets the precedence of an operator.
     * 
     * @param operator The operator
     * @return The precedence value
     */
    private static int getPrecedence(String operator) {
        return OPERATOR_PRECEDENCE.getOrDefault(operator, 0);
    }
} 