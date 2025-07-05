package com.freightfox.service;

import com.freightfox.exception.EquationNotFoundException;
import com.freightfox.exception.InvalidExpressionException;
import com.freightfox.model.*;
import com.freightfox.util.ExpressionParser;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementation of the EquationService interface.
 * Uses in-memory storage and expression trees for equation management.
 */
@Service
public class EquationServiceImpl implements EquationService {
    
    private final Map<Long, Equation> equations = new ConcurrentHashMap<>();
    private final Map<Long, ExpressionNode> expressionTrees = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    
    @Override
    public Long storeEquation(String equation) {
        try {
            // Parse and validate the expression
            ExpressionNode expressionTree = ExpressionParser.parseExpression(equation);
            
            // Generate new ID
            Long id = idCounter.getAndIncrement();
            
            // Store the equation and its expression tree
            equations.put(id, new Equation(id, equation));
            expressionTrees.put(id, expressionTree);
            
            return id;
        } catch (IllegalArgumentException e) {
            throw new InvalidExpressionException("Invalid equation: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Equation> getAllEquations() {
        return new ArrayList<>(equations.values());
    }
    
    @Override
    public Equation getEquationById(Long id) {
        Equation equation = equations.get(id);
        if (equation == null) {
            throw new EquationNotFoundException("Equation with ID " + id + " not found");
        }
        return equation;
    }
    
    @Override
    public double evaluateEquation(Long id, Map<String, Double> variables) {
        // Check if equation exists
        if (!equations.containsKey(id)) {
            throw new EquationNotFoundException("Equation with ID " + id + " not found");
        }
        
        // Get the expression tree
        ExpressionNode expressionTree = expressionTrees.get(id);
        if (expressionTree == null) {
            throw new InvalidExpressionException("Expression tree not found for equation ID " + id);
        }
        
        try {
            // Evaluate the expression tree
            return expressionTree.evaluate(variables);
        } catch (IllegalArgumentException e) {
            throw new InvalidExpressionException("Error evaluating equation: " + e.getMessage(), e);
        } catch (ArithmeticException e) {
            throw new InvalidExpressionException("Arithmetic error: " + e.getMessage(), e);
        }
    }
} 