# 🍬 Sweet Shop Management System

A full-stack Sweet Shop management system built with **Spring Boot** following **Test-Driven Development (TDD)** principles. This project demonstrates clean code practices, RESTful API design, JWT authentication, and comprehensive testing.

## 📋 Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Setup Instructions](#setup-instructions)
- [API Documentation](#api-documentation)
- [Testing](#testing)
- [AI Usage](#ai-usage)
- [Screenshots](#screenshots)

---

## ✨ Features

### 🔐 Authentication & Authorization
- User registration with email validation
- Password encryption using BCrypt
- JWT token-based authentication
- Login/logout functionality

### 🍭 Sweet Management (CRUD)
- Create new sweets with validation
- View all sweets
- Get sweet details by ID
- Update sweet information
- Delete sweets
- Category-based organization

### 📦 Order Management
- Place orders with automatic stock reduction
- Real-time stock validation
- Order total calculation
- Order history tracking
- Insufficient stock prevention

### 🛡️ Error Handling
- Custom exception classes
- Consistent error responses
- Proper HTTP status codes
- Global exception handling

---

## 🚀 Tech Stack

### Backend
- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA** - Database operations
- **Spring Security** - Authentication & authorization
- **JWT (jjwt 0.11.5)** - Token-based auth
- **PostgreSQL** - Production database
- **H2** - In-memory database for testing
- **BCrypt** - Password encryption

### Testing
- **JUnit 5** - Unit testing framework
- **Spring Boot Test** - Integration testing
- **MockMvc** - API endpoint testing
- **Maven** - Build & dependency management

### Development Practices
- **Test-Driven Development (TDD)**
- **RESTful API Design**
- **Clean Code Principles**
- **SOLID Principles**
- **Git Version Control**

---

## 📁 Project Structure
```
sweetshop/
├── src/main/java/com/harshita/sweetshop/
│   ├── config/
│   │   └── SecurityConfig.java          # Security configuration
│   ├── controller/
│   │   ├── AuthController.java          # Authentication endpoints
│   │   ├── HomeController.java          # Home & health check
│   │   ├── OrderController.java         # Order management
│   │   └── SweetController.java         # Sweet CRUD operations
│   ├── dto/
│   │   ├── AuthResponse.java           # Authentication response
│   │   ├── LoginRequest.java           # Login request payload
│   │   └── RegisterRequest.java        # Registration payload
│   ├── exception/
│   │   ├── DuplicateEmailException.java
│   │   ├── InvalidCredentialsException.java
│   │   ├── ResourceNotFoundException.java
│   │   ├── ValidationException.java
│   │   ├── InsufficientStockException.java
│   │   └── GlobalExceptionHandler.java  # Centralized error handling
│   ├── model/
│   │   ├── Order.java                   # Order entity
│   │   ├── Sweet.java                   # Sweet entity
│   │   └── User.java                    # User entity
│   ├── repository/
│   │   ├── OrderRepository.java
│   │   ├── SweetRepository.java
│   │   └── UserRepository.java
│   ├── service/
│   │   ├── JwtService.java              # JWT token operations
│   │   ├── OrderService.java            # Order business logic
│   │   ├── SweetService.java            # Sweet business logic
│   │   └── UserService.java             # User & auth logic
│   └── SweetShopApplication.java        # Main application
├── src/main/resources/
│   ├── application.properties           # Development config
│   └── application-test.properties      # Test config
├── src/test/java/com/harshita/sweetshop/
│   ├── controller/
│   │   └── AuthControllerTest.java      # Integration tests
│   └── service/
│       ├── JwtServiceTest.java          # JWT tests
│       ├── OrderServiceTest.java        # Order logic tests
│       ├── SweetServiceTest.java        # Sweet logic tests
│       └── UserServiceTest.java         # User auth tests
├── pom.xml                               # Maven dependencies
└── README.md                             # This file
```

---

## 🛠️ Setup Instructions

### Prerequisites
- **Java 17** or higher
- **PostgreSQL 14+**
- **Maven 3.6+**
- **Postman** (optional, for API testing)

### 1. Clone the Repository
```bash
git clone https://github.com/hershiee/sweet-shop.git
cd sweet-shop
```


## 🗄️ Database Configuration

### Development (H2 - In Memory)
The application uses H2 in-memory database for development and testing.

**Access H2 Console:**
1. Start application: `.\mvnw spring-boot:run`
2. Open: http://localhost:8080/h2-console
3. JDBC URL: `jdbc:h2:mem:sweetshopdb`
4. Username: `sa`
5. Password: (empty)

### Production (PostgreSQL)
For production deployment, the application supports PostgreSQL.

**Setup:**
```bash
# Create database
createdb sweetshop

# Run with production profile
.\mvnw spring-boot:run -Dspring.profiles.active=prod
```

**Environment Variables:**
- `DB_PASSWORD`: PostgreSQL password
- `JWT_SECRET`: JWT signing secret (production)

### 3. Build the Project
```bash
.\mvnw clean install
```

### 4. Run Tests
```bash
.\mvnw clean test
```

Expected output:
```
Tests run: 36, Failures: 0, Errors: 0, Skipped: 0
SUCCESS
```

### 5. Run the Application
```bash
.\mvnw spring-boot:run

```

Application will start on: `http://localhost:8080`

### 6. Verify Application is Running
```bash
curl http://localhost:8080/health
```

Expected response:
```json
{
  "status": "UP"
}
```

---

## 📡 API Documentation

### Base URL
```
http://localhost:8080
```

---

### 🏠 Health & Status

#### GET / - Welcome Message
```http
GET http://localhost:8080/
```

**Response (200 OK):**
```json
{
  "message": "Welcome to Sweet Shop API",
  "status": "Running",
  "version": "1.0"
}
```

#### GET /health - Health Check
```http
GET http://localhost:8080/health
```

**Response (200 OK):**
```json
{
  "status": "UP"
}
```

---

### 🔐 Authentication Endpoints

#### POST /api/auth/register - Register New User
```http
POST http://localhost:8080/api/auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123",
  "name": "John Doe"
}
```

**Response (201 Created):**
```json
{
  "email": "user@example.com",
  "name": "John Doe",
  "role": "USER",
  "message": "User registered successfully"
}
```

**Error Responses:**
- `400 Bad Request` - Invalid email format
- `400 Bad Request` - Password too short (< 6 characters)
- `409 Conflict` - Email already registered

---

#### POST /api/auth/login - User Login
```http
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiVVNFUiIsInN1YiI6InVzZXJAZXhhbXBsZS5jb20iLCJpYXQiOjE3MDI1MTIwMDAsImV4cCI6MTcwMjU5ODQwMH0.abcdef123456...",
  "email": "user@example.com",
  "name": "John Doe",
  "role": "USER",
  "message": "Login successful"
}
```

**Error Responses:**
- `401 Unauthorized` - Invalid email or password

---

### 🍭 Sweet Management Endpoints

#### GET /api/sweets - Get All Sweets
```http
GET http://localhost:8080/api/sweets
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "name": "Kaju Katli",
    "category": "Traditional",
    "price": 250.0,
    "stock": 100
  },
  {
    "id": 2,
    "name": "Gulab Jamun",
    "category": "Traditional",
    "price": 150.0,
    "stock": 50
  }
]
```

---

#### GET /api/sweets/{id} - Get Sweet by ID
```http
GET http://localhost:8080/api/sweets/1
```

**Response (200 OK):**
```json
{
  "id": 1,
  "name": "Kaju Katli",
  "category": "Traditional",
  "price": 250.0,
  "stock": 100
}
```

**Error Responses:**
- `404 Not Found` - Sweet not found

---

#### POST /api/sweets - Create New Sweet
```http
POST http://localhost:8080/api/sweets
Content-Type: application/json

{
  "name": "Mysore Pak",
  "category": "Traditional",
  "price": 300.0,
  "stock": 60
}
```

**Response (201 Created):**
```json
{
  "id": 3,
  "name": "Mysore Pak",
  "category": "Traditional",
  "price": 300.0,
  "stock": 60
}
```

**Error Responses:**
- `400 Bad Request` - Name is required
- `400 Bad Request` - Price must be greater than 0
- `400 Bad Request` - Stock cannot be negative

---

#### PUT /api/sweets/{id} - Update Sweet
```http
PUT http://localhost:8080/api/sweets/1
Content-Type: application/json

{
  "name": "Kaju Katli Premium",
  "category": "Premium",
  "price": 350.0,
  "stock": 150
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "name": "Kaju Katli Premium",
  "category": "Premium",
  "price": 350.0,
  "stock": 150
}
```

**Error Responses:**
- `404 Not Found` - Sweet not found
- `400 Bad Request` - Validation errors

---

#### DELETE /api/sweets/{id} - Delete Sweet
```http
DELETE http://localhost:8080/api/sweets/1
```

**Response (204 No Content)**

**Error Responses:**
- `404 Not Found` - Sweet not found

---

### 📦 Order Endpoints

#### POST /api/orders - Place Order
```http
POST http://localhost:8080/api/orders
Content-Type: application/json

{
  "sweetId": 1,
  "quantity": 3
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "sweetId": 1,
  "sweetName": "Kaju Katli",
  "quantity": 3,
  "totalAmount": 750.0,
  "status": "COMPLETED",
  "createdAt": "2025-12-14T12:30:00"
}
```

**Error Responses:**
- `404 Not Found` - Sweet not found
- `400 Bad Request` - Quantity must be greater than 0
- `400 Bad Request` - Insufficient stock

---

#### GET /api/orders/{id} - Get Order by ID
```http
GET http://localhost:8080/api/orders/1
```

**Response (200 OK):**
```json
{
  "id": 1,
  "sweetId": 1,
  "sweetName": "Kaju Katli",
  "quantity": 3,
  "totalAmount": 750.0,
  "status": "COMPLETED",
  "createdAt": "2025-12-14T12:30:00"
}
```

**Error Responses:**
- `404 Not Found` - Order not found
---

## 🧪 Testing

### Test Coverage
```
Total Tests: 36
Success Rate: 100%
```

### Test Breakdown

| Module | Tests | Coverage |
|--------|-------|----------|
| Order Service | 8 | Stock validation, order placement, retrieval |
| Sweet Service | 13 | CRUD operations, validation |
| User Service | 8 | Registration, login, password encryption |
| JWT Service | 5 | Token generation, validation |
| Auth Controller | 2 | Integration tests for auth endpoints |

### Running Tests
```bash
# Run all tests
.\mvnw test

# Run specific test class
.\mvnw test -Dtest=OrderServiceTest

# Run with coverage report
.\mvnw clean test jacoco:report
```

### Test Methodology

This project follows **strict Test-Driven Development (TDD)**:

1. **RED** - Write failing test first
2. **GREEN** - Write minimal code to pass
3. **REFACTOR** - Improve code quality

Every feature was developed using this cycle, as evidenced in the commit history.

---

## 🌐 Frontend Application

### Technology Stack
- **React 18.2.0** - Modern UI library
- **React Router v6** - Client-side routing
- **Axios** - HTTP client for API communication
- **CSS3** - Custom styling with gradients and animations

### Features

#### ✨ User Features
- User registration with email validation
- Login with JWT authentication
- Browse all available sweets
- Search sweets by name
- Filter sweets by category
- Purchase sweets with quantity selection
- Real-time stock updates
- Responsive design (mobile-friendly)

#### 🛠️ Admin Features
- Add new sweets
- Edit existing sweets
- Delete sweets
- View all sweets in table format
- Full CRUD operations
- Stock management

### Setup & Installation

#### 1. Navigate to Frontend Directory
```bash
cd frontend
```

#### 2. Install Dependencies
```bash
npm install
```

#### 3. Start Development Server
```bash
npm start
```

The application will start on: **http://localhost:3000**

### Running the Complete Application

You need to run both backend and frontend simultaneously:

#### Terminal 1 - Backend
```bash
# In project root directory
.\mvnw spring-boot:run
```
#### Terminal 2 - Frontend
```bash
# In frontend directory
cd frontend
npm start
```

### Application URLs

- **Frontend (React):** http://localhost:3000
- **Backend API:** http://localhost:8080
- **H2 Console:** http://localhost:8080/h2-console

---

## 🚀 Quick Start Guide

### 1. Start Backend
```bash
.\mvnw spring-boot:run
```
### 2. Start Frontend
```bash
cd frontend
npm start
```

### 3. Access Application
Open http://localhost:3000 in your browser

### 4. Register & Login
1. Click "Register" and create an account
2. Login with your credentials
3. Browse and purchase sweets

### 5. Admin Access (Optional)
To access admin features:
1. Login to H2 Console: http://localhost:8080/h2-console
2. Run SQL: `UPDATE USERS SET ROLE = 'ADMIN' WHERE EMAIL = 'your-email@example.com';`
3. Logout and login again
4. You'll see "Admin Panel" option in navbar

---

### Frontend Environment

The frontend is configured to communicate with the backend via a proxy:

**package.json:**
```json
"proxy": "http://localhost:8080"
```

### CORS Configuration

The backend is configured to accept requests from the frontend:

**WebConfig.java:**
```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
```

---

## 📦 Project Structure
```
sweet-shop/
├── frontend/                        # React Frontend Application
│   ├── public/
│   ├── src/
│   │   ├── components/
│   │   │   ├── Auth/               # Login & Registration
│   │   │   ├── Dashboard/          # Sweet browsing & purchase
│   │   │   ├── Admin/              # Admin CRUD operations
│   │   │   └── Navbar/             # Navigation bar
│   │   ├── services/
│   │   │   ├── api.js             # API calls with axios
│   │   │   └── auth.js            # Auth utilities
│   │   ├── App.js                 # Main app with routing
│   │   └── App.css                # Global styles
│   ├── package.json
│   └── README.md
├── src/                            # Backend Spring Boot Application
│   ├── main/
│   │   ├── java/
│   │   │   └── com/harshita/sweetshop/
│   │   │       ├── config/        # Configuration classes
│   │   │       ├── controller/    # REST Controllers
│   │   │       ├── service/       # Business Logic
│   │   │       ├── repository/    # Data Access
│   │   │       ├── model/         # Entity classes
│   │   │       ├── dto/           # Data Transfer Objects
│   │   │       └── exception/     # Exception handling
│   │   └── resources/
│   │       ├── application.properties
│   │       └── application-prod.properties
│   └── test/
├── screenshots/                    # Application screenshots
├── pom.xml
└── README.md
```

---

## 🎨 UI/UX Highlights

### Design Features
- **Modern Gradient Design:** Purple gradient theme throughout
- **Smooth Animations:** Fade-in, slide-up, and hover effects
- **Responsive Layout:** Works on desktop, tablet, and mobile
- **User Feedback:** Loading states, error messages, success notifications
- **Intuitive Navigation:** Clear routing and protected routes
- **Accessibility:** Proper form labels and semantic HTML

### Color Scheme
- Primary: `#667eea` (Purple)
- Secondary: `#764ba2` (Deep Purple)
- Background: `#f5f5f5` (Light Gray)
- Success: `#4caf50` (Green)
- Error: `#f44336` (Red)

---



## 🤖 My AI Usage

### Tools Used
- **Claude AI (Anthropic)** - Primary development assistant and TDD guide
- **CharGPT** - For initial boilerplate code and debugging

### How I Used AI

#### Test-Driven Development Guidance
Claude AI acted as a TDD mentor, enforcing the RED-GREEN-REFACTOR cycle throughout development. For each of the 35 tests:

1. **RED Phase**: AI helped me write failing tests first, ensuring I understood what I was building
2. **GREEN Phase**: AI provided implementation guidance, but I had to understand and modify the code
3. **REFACTOR Phase**: AI suggested improvements while maintaining test coverage

#### What AI Helped With

**Architecture & Design:**
- Spring Boot project structure and best practices
- RESTful API design patterns
- Exception handling architecture
- Security configuration strategies

**Technical Implementation:**
- BCrypt password encryption integration
- JWT token generation and validation logic
- Spring Security configuration for testing
- JPA entity relationships
- Transaction management with `@Transactional`

**Testing:**
- JUnit 5 test structure and assertions
- MockMvc for integration testing
- Test data setup and teardown
- Edge case identification

**Documentation:**
- API endpoint documentation structure
- README formatting and organization
- Code comments and JavaDoc

#### What I Built Myself

**Core Decisions:**
- Database schema design (User, Sweet, Order entities)
- Business logic and validation rules
- API endpoint structure and versioning
- Error response format
- Commit strategy and messages
- Test case scenarios and coverage strategy

**Problem Solving:**
- Debugging BCrypt password matching issues
- Resolving Spring Security blocking test requests
- Integration between services and controllers
- Order flow and stock management logic

**Development Process:**
- Git workflow and branching
- Incremental feature development
- Code review and refactoring decisions
- Exception handling hierarchy

### Challenges & Learning

#### Challenge 1: BCrypt Password Verification
**Problem**: Initial confusion between `encode()` (for hashing) and `matches()` (for verification).

**Solution**: AI explained that BCrypt generates different hashes for the same password (due to salt), but `matches()` handles this correctly. Tested thoroughly with unit tests to verify.

#### Challenge 2: Spring Security Blocking Tests
**Problem**: After adding Spring Security, all requests returned 401 Unauthorized, even in tests.

**Solution**: AI helped create `SecurityConfig` with `.permitAll()` for testing phase. Learned about security filter chains and will properly configure JWT filters in production.

#### Challenge 3: TDD Discipline
**Problem**: Natural tendency to jump to implementation before writing tests.

**Solution**: AI enforced "RED commit first" rule, which forced me to think through requirements before coding. This improved code quality significantly.

#### Challenge 4: Exception Handling Hierarchy
**Problem**: Started with generic `RuntimeException` everywhere, leading to unclear error responses.

**Solution**: AI suggested custom exception classes with proper HTTP status codes. Refactored all services to use specific exceptions, making debugging easier.

### Reflection

#### Time Impact
- **Without AI**: Estimated 2-3 weeks (based on similar projects)
- **With AI**: Completed in 2 days with deeper understanding
- **Key Benefit**: Not speed alone, but quality of learning

#### What I Learned
1. **TDD is powerful**: Writing tests first actually reduces debugging time
2. **Small commits matter**: Each RED-GREEN cycle creates natural checkpoints
3. **AI as a teacher**: Best used for explanations, not just code generation
4. **Understanding > Working code**: AI forced me to understand "why" not just "how"

#### Honest Assessment
- **Code generated by AI**: ~40% (boilerplate, DTOs, initial structures)
- **Code I wrote/modified**: ~60% (business logic, validation, custom implementations)
- **Concepts I didn't know before**: JWT, BCrypt, Spring Security filters, TDD methodology
- **Would I have learned without AI**: Probably, but much slower and with more frustration

### AI Usage in Commits

Every commit where AI contributed includes co-author attribution:
```bash
Co-authored-by: Claude AI <claude@anthropic.com>
```

Examples:
- Initial project structure
- Exception handling framework
- JWT service implementation
- Test boilerplate generation

### Key Takeaway

AI didn't make me a better developer by writing code for me. It made me better by:
- Enforcing best practices (TDD, SOLID principles)
- Providing immediate feedback on design decisions
- Explaining complex concepts (JWT, BCrypt, Spring Security)
- Catching potential issues before they became bugs
- Acting as a pair programming partner

The result: **Better code, faster learning, and genuine understanding** of what I built.

---

## 📸 Screenshots

### Application Running
![Application Startup](screenshots/app-running.png)
*Spring Boot application successfully started on port 8080*

### API Testing - Postman
![Home Endpoint](screenshots/home-endpoint.png)
*Home endpoint returning welcome message*

![Register User](screenshots/register-user.png)
*User registration with validation*

![Login Success](screenshots/login-success.png)
*Successful login with JWT token*

![Get All Sweets](screenshots/get-sweets.png)
*Retrieving all sweets from database*

![Create Sweet](screenshots/create-sweet.png)
*Creating a new sweet with validation*

![Error Handling](screenshots/error-response.png)
*Consistent error responses with proper status codes*

### Test Results
![All Tests Passing](screenshots/tests-passing.png)
*36/356tests passing with 100% success rate*

---


## 👤 Author
Harshita Gupta

GitHub: @hershiee
Project: Sweet Shop Management System

