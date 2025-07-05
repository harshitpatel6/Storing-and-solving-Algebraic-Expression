package com.freightfox.controller;

import com.freightfox.exception.EquationNotFoundException;
import com.freightfox.exception.InvalidExpressionException;
import com.freightfox.model.*;
import com.freightfox.service.EquationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for equation operations.
 * Provides endpoints for storing, retrieving, and evaluating algebraic equations.
 */
@RestController
@RequestMapping("/api/equations")
@CrossOrigin(origins = "*")
public class EquationController {
    
    private final EquationService equationService;
    
    @Autowired
    public EquationController(EquationService equationService) {
        this.equationService = equationService;
    }
    
    /**
     * Stores a new equation.
     * 
     * @param request The equation to store
     * @return Response with success message and equation ID
     */
    @PostMapping("/store")
    public ResponseEntity<StoreEquationResponse> storeEquation(@Valid @RequestBody StoreEquationRequest request) {
        try {
            Long equationId = equationService.storeEquation(request.getEquation());
            StoreEquationResponse response = new StoreEquationResponse("Equation stored successfully", equationId);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (InvalidExpressionException e) {
            throw e;
        }
    }
    
    /**
     * Retrieves all stored equations.
     * 
     * @return Response with all equations in the expected format
     */
    @GetMapping
    public ResponseEntity<GetAllEquationsResponse> getAllEquations() {
        List<Equation> equations = equationService.getAllEquations();
        
        List<GetAllEquationsResponse.EquationResponse> equationResponses = equations.stream()
                .map(eq -> new GetAllEquationsResponse.EquationResponse(
                        String.valueOf(eq.getId()), 
                        eq.getEquation()))
                .toList();
        
        GetAllEquationsResponse response = new GetAllEquationsResponse(equationResponses);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Retrieves a specific equation by ID.
     * 
     * @param id The equation ID
     * @return The equation
     */
    @GetMapping("/{id}")
    public ResponseEntity<Equation> getEquationById(@PathVariable Long id) {
        try {
            Equation equation = equationService.getEquationById(id);
            return ResponseEntity.ok(equation);
        } catch (EquationNotFoundException e) {
            throw e;
        }
    }
    
    /**
     * Evaluates an equation with given variable values.
     * 
     * @param id The equation ID
     * @param request The variable values
     * @return The evaluation result
     */
    @PostMapping("/{id}/evaluate")
    public ResponseEntity<EvaluateEquationResponse> evaluateEquation(
            @PathVariable Long id,
            @Valid @RequestBody EvaluateEquationRequest request) {
        try {
            double result = equationService.evaluateEquation(id, request.getVariables());
            Equation equation = equationService.getEquationById(id);
            
            EvaluateEquationResponse response = new EvaluateEquationResponse(
                id, equation.getEquation(), request.getVariables(), result);
            
            return ResponseEntity.ok(response);
        } catch (EquationNotFoundException | InvalidExpressionException e) {
            throw e;
        }
    }
    
    /**
     * Exception handler for EquationNotFoundException.
     * 
     * @param e The exception
     * @return Error response
     */
    @ExceptionHandler(EquationNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleEquationNotFoundException(EquationNotFoundException e) {
        Map<String, String> error = Map.of("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    /**
     * Exception handler for InvalidExpressionException.
     * 
     * @param e The exception
     * @return Error response
     */
    @ExceptionHandler(InvalidExpressionException.class)
    public ResponseEntity<Map<String, String>> handleInvalidExpressionException(InvalidExpressionException e) {
        Map<String, String> error = Map.of("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    
    /**
     * Exception handler for validation errors.
     * 
     * @param e The exception
     * @return Error response
     */
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(
            org.springframework.web.bind.MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");
        
        Map<String, String> error = Map.of("error", errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
} 