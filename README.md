# Equation Solver - Spring Boot Backend

A Spring Boot REST API for storing and solving algebraic equations using postfix notation and expression trees.

## 🎯 Features

- **Store Equations**: Parse and store algebraic expressions using Shunting Yard algorithm
- **Expression Trees**: Build postfix expression trees for efficient evaluation
- **Variable Substitution**: Evaluate equations with custom variable values
- **RESTful API**: Clean REST endpoints with proper error handling
- **Validation**: Comprehensive input validation and error handling
- **Unit Tests**: Complete test coverage for all components

## 🚀 Quick Start

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Setup Instructions

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd equation-solver
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the API**
   - Base URL: `http://localhost:8080`
   - API Documentation: `http://localhost:8080/api/equations`

## 📚 API Documentation

### 1. Store Equation

**Endpoint:** `POST /api/equations/store`

**Request Body:**
```json
{
  "equation": "3x + 2y - z"
}
```

**Response:**
```json
{
  "message": "Equation stored successfully",
  "equationId": 1
}
```

**Example:**
```bash
curl -X POST http://localhost:8080/api/equations/store \
  -H "Content-Type: application/json" \
  -d '{"equation": "3x + 2y - z"}'
```

### 2. Get All Equations

**Endpoint:** `GET /api/equations`

**Response:**
```json
[
  {
    "id": 1,
    "equation": "3x + 2y - z"
  },
  {
    "id": 2,
    "equation": "x^2 + y^2 - 4"
  }
]
```

**Example:**
```bash
curl -X GET http://localhost:8080/api/equations
```

### 3. Get Equation by ID

**Endpoint:** `GET /api/equations/{id}`

**Response:**
```json
{
  "id": 1,
  "equation": "3x + 2y - z"
}
```

**Example:**
```bash
curl -X GET http://localhost:8080/api/equations/1
```

### 4. Evaluate Equation

**Endpoint:** `POST /api/equations/{id}/evaluate`

**Request Body:**
```json
{
  "variables": {
    "x": 2,
    "y": 3,
    "z": 1
  }
}
```

**Response:**
```json
{
  "equationId": 1,
  "equation": "3x + 2y - z",
  "variables": {
    "x": 2,
    "y": 3,
    "z": 1
  },
  "result": 10
}
```

**Example:**
```bash
curl -X POST http://localhost:8080/api/equations/1/evaluate \
  -H "Content-Type: application/json" \
  -d '{"variables": {"x": 2, "y": 3, "z": 1}}'
```

## 🧪 Running Tests

### Run All Tests
```bash
mvn test
```

### Run Specific Test Classes
```bash
mvn test -Dtest=ExpressionParserTest
mvn test -Dtest=EquationServiceTest
mvn test -Dtest=EquationControllerTest
```

### Test Coverage
```bash
mvn test jacoco:report
```

## 🏗️ Project Structure

```
src/
├── main/java/com/freightfox/
│   ├── EquationSolverApplication.java    # Main Spring Boot application
│   ├── controller/
│   │   └── EquationController.java       # REST API endpoints
│   ├── service/
│   │   ├── EquationService.java          # Service interface
│   │   └── EquationServiceImpl.java      # Service implementation
│   ├── model/
│   │   ├── Equation.java                 # Equation entity
│   │   ├── ExpressionNode.java           # Abstract expression node
│   │   ├── ConstantNode.java             # Constant node
│   │   ├── VariableNode.java             # Variable node
│   │   ├── OperatorNode.java             # Operator node
│   │   ├── StoreEquationRequest.java     # Request DTOs
│   │   ├── StoreEquationResponse.java
│   │   ├── EvaluateEquationRequest.java
│   │   └── EvaluateEquationResponse.java
│   ├── util/
│   │   └── ExpressionParser.java         # Shunting Yard algorithm
│   └── exception/
│       ├── EquationNotFoundException.java
│       └── InvalidExpressionException.java
└── test/java/com/freightfox/
    ├── controller/
    │   └── EquationControllerTest.java
    ├── service/
    │   └── EquationServiceTest.java
    └── util/
        └── ExpressionParserTest.java
```

## 🔧 Technical Implementation

### Expression Parsing
- **Shunting Yard Algorithm**: Converts infix expressions to postfix notation
- **Tokenization**: Breaks expressions into tokens (numbers, variables, operators)
- **Operator Precedence**: Handles mathematical operator precedence correctly

### Expression Trees
- **Tree Structure**: Binary tree representation of expressions
- **Node Types**: Constant, Variable, and Operator nodes
- **Evaluation**: Recursive evaluation with variable substitution

### Supported Operations
- **Arithmetic**: `+`, `-`, `*`, `/`, `^` (exponentiation)
- **Parentheses**: Full support for grouping expressions
- **Variables**: Single-letter variables (a-z, A-Z)
- **Numbers**: Integer and decimal numbers

### Error Handling
- **Validation**: Input validation with descriptive error messages
- **Exceptions**: Custom exceptions for different error scenarios
- **HTTP Status Codes**: Proper HTTP status codes for different errors

## 📝 Example Expressions

### Simple Expressions
- `2 + 3` → 5
- `10 - 5` → 5
- `4 * 3` → 12
- `15 / 3` → 5

### Variable Expressions
- `3x + 2y - z` with x=2, y=3, z=1 → 10
- `x^2 + y^2 - 4` with x=3, y=4 → 21
- `x * (y + z) - 7` with x=2, y=3, z=1 → 1

### Complex Expressions
- `(2 + 3) * 4` → 20
- `2 + 3 * 4` → 14 (operator precedence)
- `x^2 + y^2` with x=5, y=12 → 169

## 🚨 Error Responses

### 400 Bad Request
```json
{
  "error": "Invalid equation: Invalid character in expression: @"
}
```

### 404 Not Found
```json
{
  "error": "Equation with ID 999 not found"
}
```

### 400 Validation Error
```json
{
  "error": "equation: Equation cannot be empty"
}
```

## 🔒 Security Considerations

- Input validation for all expressions
- Protection against division by zero
- Proper error handling without information leakage
- CORS enabled for cross-origin requests

## 🚀 Deployment

### Build JAR
```bash
mvn clean package
```

### Run JAR
```bash
java -jar target/equation-solver-1.0-SNAPSHOT.jar
```

### Docker (Optional)
```bash
docker build -t equation-solver .
docker run -p 8080:8080 equation-solver
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Run the test suite
6. Submit a pull request

## 📄 License

This project is licensed under the MIT License.

## 🆘 Support

For issues and questions:
1. Check the existing issues
2. Create a new issue with detailed description
3. Include error messages and steps to reproduce

---

**Happy Coding! 🎉** 
