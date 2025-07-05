package com.freightfox.service;

import com.freightfox.exception.EquationNotFoundException;
import com.freightfox.exception.InvalidExpressionException;
import com.freightfox.model.Equation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

/**
 * Unit tests for EquationService implementation.
 */
@DisplayName("EquationService Tests")
class EquationServiceTest {
    
    private EquationService equationService;
    
    @BeforeEach
    void setUp() {
        equationService = new EquationServiceImpl();
    }
    
    @Test
    @DisplayName("Should store equation successfully")
    void testStoreEquation() {
        Long id = equationService.storeEquation("2 + 3");
        assertNotNull(id);
        assertTrue(id > 0);
    }
    
    @Test
    @DisplayName("Should store equation with variables")
    void testStoreEquationWithVariables() {
        Long id = equationService.storeEquation("x + y");
        assertNotNull(id);
        assertTrue(id > 0);
    }
    
    @Test
    @DisplayName("Should throw exception for invalid equation")
    void testStoreInvalidEquation() {
        assertThrows(InvalidExpressionException.class, () -> {
            equationService.storeEquation("2 + @ 3");
        });
    }
    
    @Test
    @DisplayName("Should retrieve all equations")
    void testGetAllEquations() {
        // Store some equations
        equationService.storeEquation("2 + 3");
        equationService.storeEquation("x + y");
        
        List<Equation> equations = equationService.getAllEquations();
        assertEquals(2, equations.size());
    }
    
    @Test
    @DisplayName("Should retrieve equation by ID")
    void testGetEquationById() {
        Long id = equationService.storeEquation("2 + 3");
        Equation equation = equationService.getEquationById(id);
        
        assertEquals(id, equation.getId());
        assertEquals("2 + 3", equation.getEquation());
    }
    
    @Test
    @DisplayName("Should throw exception for non-existent equation ID")
    void testGetEquationByIdNotFound() {
        assertThrows(EquationNotFoundException.class, () -> {
            equationService.getEquationById(999L);
        });
    }
    
    @Test
    @DisplayName("Should evaluate equation with constants")
    void testEvaluateEquationWithConstants() {
        Long id = equationService.storeEquation("2 + 3 * 4");
        double result = equationService.evaluateEquation(id, Map.of());
        assertEquals(14.0, result, 0.001);
    }
    
    @Test
    @DisplayName("Should evaluate equation with variables")
    void testEvaluateEquationWithVariables() {
        Long id = equationService.storeEquation("3x + 2y - z");
        Map<String, Double> variables = Map.of("x", 2.0, "y", 3.0, "z", 1.0);
        double result = equationService.evaluateEquation(id, variables);
        assertEquals(11.0, result, 0.001);
    }
    
    @Test
    @DisplayName("Should evaluate complex equation")
    void testEvaluateComplexEquation() {
        Long id = equationService.storeEquation("x * (y + z) - 7");
        Map<String, Double> variables = Map.of("x", 2.0, "y", 3.0, "z", 1.0);
        double result = equationService.evaluateEquation(id, variables);
        assertEquals(1.0, result, 0.001);
    }
    
    @Test
    @DisplayName("Should throw exception when evaluating non-existent equation")
    void testEvaluateNonExistentEquation() {
        assertThrows(EquationNotFoundException.class, () -> {
            equationService.evaluateEquation(999L, Map.of("x", 2.0));
        });
    }
    
    @Test
    @DisplayName("Should throw exception for missing variables")
    void testEvaluateWithMissingVariables() {
        Long id = equationService.storeEquation("x + y");
        assertThrows(InvalidExpressionException.class, () -> {
            equationService.evaluateEquation(id, Map.of("x", 2.0)); // y is missing
        });
    }
    
    @Test
    @DisplayName("Should handle division by zero")
    void testDivisionByZero() {
        Long id = equationService.storeEquation("10 / 0");
        assertThrows(InvalidExpressionException.class, () -> {
            equationService.evaluateEquation(id, Map.of());
        });
    }
    
    @Test
    @DisplayName("Should handle exponentiation")
    void testExponentiation() {
        Long id = equationService.storeEquation("x^2 + y^2");
        Map<String, Double> variables = Map.of("x", 3.0, "y", 4.0);
        double result = equationService.evaluateEquation(id, variables);
        assertEquals(25.0, result, 0.001);
    }
    
    @Test
    @DisplayName("Should handle decimal numbers")
    void testDecimalNumbers() {
        Long id = equationService.storeEquation("2.5 + 3.5");
        double result = equationService.evaluateEquation(id, Map.of());
        assertEquals(6.0, result, 0.001);
    }
    
    @Test
    @DisplayName("Should handle multiple equations with different IDs")
    void testMultipleEquations() {
        Long id1 = equationService.storeEquation("x + y");
        Long id2 = equationService.storeEquation("a * b");
        
        assertNotEquals(id1, id2);
        
        Map<String, Double> variables1 = Map.of("x", 2.0, "y", 3.0);
        Map<String, Double> variables2 = Map.of("a", 4.0, "b", 5.0);
        
        assertEquals(5.0, equationService.evaluateEquation(id1, variables1), 0.001);
        assertEquals(20.0, equationService.evaluateEquation(id2, variables2), 0.001);
    }
} 