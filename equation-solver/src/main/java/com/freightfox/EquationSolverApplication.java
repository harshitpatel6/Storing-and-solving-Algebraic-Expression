package com.freightfox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application class for the Equation Solver.
 * This application provides REST APIs to store and evaluate algebraic equations
 * using postfix notation and expression trees.
 */
@SpringBootApplication
public class EquationSolverApplication {

    public static void main(String[] args) {
        SpringApplication.run(EquationSolverApplication.class, args);
    }
} 