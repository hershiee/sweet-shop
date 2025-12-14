# Getting Started

## Quick Start Guide

### 1. Prerequisites
- Java 17 or higher
- PostgreSQL 14+
- Maven 3.6+

### 2. Database Setup
```bash
# Create database
createdb sweetshop

# Or using psql
psql -U postgres
CREATE DATABASE sweetshop;
\q
```

### 3. Configure Database
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/sweetshop
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

### 4. Build & Run
```bash
# Build
.\mvnw.cmd clean install

# Run tests
.\mvnw.cmd test

# Start application
.\mvnw spring-boot:run
```

### 5. Test API
Open Postman and import the requests from README.md

---

## Troubleshooting

### Issue: Port 8080 already in use
**Solution:**
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Mac/Linux
lsof -i :8080
kill -9 <PID>
```

### Issue: Database connection refused
**Solution:**
- Ensure PostgreSQL is running
- Check username/password in application.properties
- Verify database exists: `psql -l`

### Issue: Tests failing
**Solution:**
```bash
.\mvnw.cmd clean test -X  # Run with debug output
```

---

## API Testing with Postman

### Collection Import
1. Create a new collection in Postman
2. Copy API endpoints from README.md
3. Set base URL: `http://localhost:8080`

### Test Sequence
1. Register user → Copy email
2. Login → Copy JWT token
3. Create sweets
4. Place orders
5. Test error scenarios

---

## Contact

For issues or questions:
- GitHub Issues: https://github.com/hershiee/sweet-shop/issues
- Email: gharshita035@gmail.com