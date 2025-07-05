package com.freightfox.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freightfox.model.*;
import com.freightfox.service.EquationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Unit tests for EquationController REST endpoints.
 */
@WebMvcTest(EquationController.class)
@DisplayName("EquationController Tests")
class EquationControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private EquationService equationService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @BeforeEach
    void setUp() {
        // Setup default mock behaviors
    }
    
    @Test
    @DisplayName("Should store equation successfully")
    void testStoreEquation() throws Exception {
        // Given
        StoreEquationRequest request = new StoreEquationRequest("2 + 3");
        when(equationService.storeEquation("2 + 3")).thenReturn(1L);
        
        // When & Then
        mockMvc.perform(post("/api/equations/store")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Equation stored successfully"))
                .andExpect(jsonPath("$.equationId").value(1));
    }
    
    @Test
    @DisplayName("Should return 400 for invalid equation")
    void testStoreInvalidEquation() throws Exception {
        // Given
        StoreEquationRequest request = new StoreEquationRequest("2 + @ 3");
        when(equationService.storeEquation("2 + @ 3"))
                .thenThrow(new com.freightfox.exception.InvalidExpressionException("Invalid equation"));
        
        // When & Then
        mockMvc.perform(post("/api/equations/store")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }
    
    @Test
    @DisplayName("Should return 400 for empty equation")
    void testStoreEmptyEquation() throws Exception {
        // Given
        StoreEquationRequest request = new StoreEquationRequest("");
        
        // When & Then
        mockMvc.perform(post("/api/equations/store")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    @DisplayName("Should get all equations")
    void testGetAllEquations() throws Exception {
        // Given
        List<Equation> equations = Arrays.asList(
                new Equation(1L, "2 + 3"),
                new Equation(2L, "x + y")
        );
        when(equationService.getAllEquations()).thenReturn(equations);
        
        // When & Then
        mockMvc.perform(get("/api/equations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.equations").isArray())
                .andExpect(jsonPath("$.equations[0].equationId").value("1"))
                .andExpect(jsonPath("$.equations[0].equation").value("2 + 3"))
                .andExpect(jsonPath("$.equations[1].equationId").value("2"))
                .andExpect(jsonPath("$.equations[1].equation").value("x + y"));
    }
    
    @Test
    @DisplayName("Should get equation by ID")
    void testGetEquationById() throws Exception {
        // Given
        Equation equation = new Equation(1L, "2 + 3");
        when(equationService.getEquationById(1L)).thenReturn(equation);
        
        // When & Then
        mockMvc.perform(get("/api/equations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.equation").value("2 + 3"));
    }
    
    @Test
    @DisplayName("Should return 404 for non-existent equation")
    void testGetEquationByIdNotFound() throws Exception {
        // Given
        when(equationService.getEquationById(999L))
                .thenThrow(new com.freightfox.exception.EquationNotFoundException("Equation not found"));
        
        // When & Then
        mockMvc.perform(get("/api/equations/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").exists());
    }
    
    @Test
    @DisplayName("Should evaluate equation successfully")
    void testEvaluateEquation() throws Exception {
        // Given
        EvaluateEquationRequest request = new EvaluateEquationRequest(Map.of("x", 2.0, "y", 3.0));
        when(equationService.evaluateEquation(1L, Map.of("x", 2.0, "y", 3.0))).thenReturn(5.0);
        when(equationService.getEquationById(1L)).thenReturn(new Equation(1L, "x + y"));
        
        // When & Then
        mockMvc.perform(post("/api/equations/1/evaluate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.equationId").value(1))
                .andExpect(jsonPath("$.equation").value("x + y"))
                .andExpect(jsonPath("$.result").value(5.0))
                .andExpect(jsonPath("$.variables.x").value(2.0))
                .andExpect(jsonPath("$.variables.y").value(3.0));
    }
    
    @Test
    @DisplayName("Should return 404 when evaluating non-existent equation")
    void testEvaluateNonExistentEquation() throws Exception {
        // Given
        EvaluateEquationRequest request = new EvaluateEquationRequest(Map.of("x", 2.0));
        when(equationService.evaluateEquation(999L, Map.of("x", 2.0)))
                .thenThrow(new com.freightfox.exception.EquationNotFoundException("Equation not found"));
        
        // When & Then
        mockMvc.perform(post("/api/equations/999/evaluate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").exists());
    }
    
    @Test
    @DisplayName("Should return 400 for invalid evaluation request")
    void testEvaluateInvalidRequest() throws Exception {
        // Given
        EvaluateEquationRequest request = new EvaluateEquationRequest(Map.of());
        
        // When & Then
        mockMvc.perform(post("/api/equations/1/evaluate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    @DisplayName("Should return 400 for missing variables")
    void testEvaluateMissingVariables() throws Exception {
        // Given
        EvaluateEquationRequest request = new EvaluateEquationRequest(Map.of("x", 2.0));
        when(equationService.evaluateEquation(1L, Map.of("x", 2.0)))
                .thenThrow(new com.freightfox.exception.InvalidExpressionException("Missing variable y"));
        
        // When & Then
        mockMvc.perform(post("/api/equations/1/evaluate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }
    
    @Test
    @DisplayName("Should handle complex equation evaluation")
    void testEvaluateComplexEquation() throws Exception {
        // Given
        EvaluateEquationRequest request = new EvaluateEquationRequest(Map.of("x", 2.0, "y", 3.0, "z", 1.0));
        when(equationService.evaluateEquation(1L, Map.of("x", 2.0, "y", 3.0, "z", 1.0))).thenReturn(10.0);
        when(equationService.getEquationById(1L)).thenReturn(new Equation(1L, "3x + 2y - z"));
        
        // When & Then
        mockMvc.perform(post("/api/equations/1/evaluate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.equationId").value(1))
                .andExpect(jsonPath("$.equation").value("3x + 2y - z"))
                .andExpect(jsonPath("$.result").value(10.0));
    }
} 