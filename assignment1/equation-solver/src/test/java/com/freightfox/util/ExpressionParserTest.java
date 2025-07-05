package com.freightfox.util;

import com.freightfox.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

/**
 * Unit tests for ExpressionParser utility class.
 */
@DisplayName("ExpressionParser Tests")
class ExpressionParserTest {
    

    
    @Test
    @DisplayName("Should parse simple addition")
    void testSimpleAddition() {
        ExpressionNode node = ExpressionParser.parseExpression("2 + 3");
        assertEquals(5.0, node.evaluate(Map.of()), 0.001);
    }
    
    @Test
    @DisplayName("Should parse simple subtraction")
    void testSimpleSubtraction() {
        ExpressionNode node = ExpressionParser.parseExpression("5 - 3");
        assertEquals(2.0, node.evaluate(Map.of()), 0.001);
    }
    
    @Test
    @DisplayName("Should parse simple multiplication")
    void testSimpleMultiplication() {
        ExpressionNode node = ExpressionParser.parseExpression("4 * 3");
        assertEquals(12.0, node.evaluate(Map.of()), 0.001);
    }
    
    @Test
    @DisplayName("Should parse simple division")
    void testSimpleDivision() {
        ExpressionNode node = ExpressionParser.parseExpression("10 / 2");
        assertEquals(5.0, node.evaluate(Map.of()), 0.001);
    }
    
    @Test
    @DisplayName("Should parse exponentiation")
    void testExponentiation() {
        ExpressionNode node = ExpressionParser.parseExpression("2 ^ 3");
        assertEquals(8.0, node.evaluate(Map.of()), 0.001);
    }
    
    @Test
    @DisplayName("Should parse expression with variables")
    void testExpressionWithVariables() {
        ExpressionNode node = ExpressionParser.parseExpression("x + y");
        Map<String, Double> variables = Map.of("x", 2.0, "y", 3.0);
        assertEquals(5.0, node.evaluate(variables), 0.001);
    }
    
    @Test
    @DisplayName("Should parse complex expression with variables")
    void testComplexExpressionWithVariables() {
        ExpressionNode node = ExpressionParser.parseExpression("3x + 2y - z");
        Map<String, Double> variables = Map.of("x", 2.0, "y", 3.0, "z", 1.0);
        assertEquals(11.0, node.evaluate(variables), 0.001);
    }
    
    @Test
    @DisplayName("Should parse expression with parentheses")
    void testExpressionWithParentheses() {
        ExpressionNode node = ExpressionParser.parseExpression("(2 + 3) * 4");
        assertEquals(20.0, node.evaluate(Map.of()), 0.001);
    }
    
    @Test
    @DisplayName("Should parse complex expression with parentheses")
    void testComplexExpressionWithParentheses() {
        ExpressionNode node = ExpressionParser.parseExpression("x * (y + z) - 7");
        Map<String, Double> variables = Map.of("x", 2.0, "y", 3.0, "z", 1.0);
        assertEquals(1.0, node.evaluate(variables), 0.001);
    }
    
    @Test
    @DisplayName("Should parse expression with operator precedence")
    void testOperatorPrecedence() {
        ExpressionNode node = ExpressionParser.parseExpression("2 + 3 * 4");
        assertEquals(14.0, node.evaluate(Map.of()), 0.001);
    }
    
    @Test
    @DisplayName("Should parse expression with multiple operators")
    void testMultipleOperators() {
        ExpressionNode node = ExpressionParser.parseExpression("x^2 + y^2 - 4");
        Map<String, Double> variables = Map.of("x", 3.0, "y", 4.0);
        assertEquals(21.0, node.evaluate(variables), 0.001);
    }
    
    @Test
    @DisplayName("Should handle decimal numbers")
    void testDecimalNumbers() {
        ExpressionNode node = ExpressionParser.parseExpression("2.5 + 3.5");
        assertEquals(6.0, node.evaluate(Map.of()), 0.001);
    }
    
    @Test
    @DisplayName("Should throw exception for invalid characters")
    void testInvalidCharacters() {
        assertThrows(IllegalArgumentException.class, () -> {
            ExpressionParser.parseExpression("2 + 3 @ 4");
        });
    }
    
    @Test
    @DisplayName("Should throw exception for mismatched parentheses")
    void testMismatchedParentheses() {
        assertThrows(IllegalArgumentException.class, () -> {
            ExpressionParser.parseExpression("(2 + 3");
        });
    }
    
    @Test
    @DisplayName("Should throw exception for empty expression")
    void testEmptyExpression() {
        assertThrows(IllegalArgumentException.class, () -> {
            ExpressionParser.parseExpression("");
        });
    }
    
    @Test
    @DisplayName("Should throw exception for null expression")
    void testNullExpression() {
        assertThrows(IllegalArgumentException.class, () -> {
            ExpressionParser.parseExpression(null);
        });
    }
    
    @Test
    @DisplayName("Should handle single number")
    void testSingleNumber() {
        ExpressionNode node = ExpressionParser.parseExpression("42");
        assertEquals(42.0, node.evaluate(Map.of()), 0.001);
    }
    
    @Test
    @DisplayName("Should handle single variable")
    void testSingleVariable() {
        ExpressionNode node = ExpressionParser.parseExpression("x");
        Map<String, Double> variables = Map.of("x", 5.0);
        assertEquals(5.0, node.evaluate(variables), 0.001);
    }
    
    @Test
    @DisplayName("Should throw exception for missing variable")
    void testMissingVariable() {
        ExpressionNode node = ExpressionParser.parseExpression("x + y");
        assertThrows(IllegalArgumentException.class, () -> {
            node.evaluate(Map.of("x", 2.0)); // y is missing
        });
    }
    
    @Test
    @DisplayName("Should handle division by zero")
    void testDivisionByZero() {
        ExpressionNode node = ExpressionParser.parseExpression("10 / 0");
        assertThrows(ArithmeticException.class, () -> {
            node.evaluate(Map.of());
        });
    }
} 