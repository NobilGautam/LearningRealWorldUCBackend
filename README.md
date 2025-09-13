# ForkCast Backend

A multi-module Spring Boot application with Jersey JAX-RS, JWT authentication, and role-based access control.

## Project Structure

```
backend/
├── common/              # Common utilities, enums, constants, exceptions
│   ├── enums/          # Role enum (Admin, OutletManager)
│   ├── constants/      # Security constants
│   ├── exceptions/     # Custom exceptions
│   └── utils/          # JWT utility class
├── dao/                # Data Access Objects, Models, DTOs
│   ├── entities/       # JPA entities (User)
│   ├── dto/           # Data Transfer Objects
│   └── repository/    # JPA repositories
├── service/            # Business logic interfaces and implementations
│   ├── AuthService    # Authentication service
│   └── HealthService  # Health check service
└── app/                # Main application, controllers, configurations
    ├── config/        # Spring configurations (Security, Jersey)
    ├── controllers/   # JAX-RS controllers
    ├── security/      # JWT authentication filter
    └── ForkCastApplication.java # Main application class
```

## Features

- **Multi-module Maven architecture** for better code organization
- **Jersey JAX-RS** for REST API endpoints
- **Spring Security** with JWT authentication
- **Role-Based Access Control (RBAC)** with Admin and Outlet Manager roles
- **JWT tokens stored in HTTP-only cookies** for frontend integration
- **H2 in-memory database** for development
- **Lombok** for reducing boilerplate code

## API Endpoints

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `POST /api/auth/logout` - User logout

### Health Check
- `GET /api/health` - Application health status

## Security Configuration

- **Public endpoints**: `/api/auth/login`, `/api/auth/register`, `/api/health`
- **Admin endpoints**: `/api/admin/**` (requires ADMIN role)
- **JWT authentication** for all other endpoints
- **CORS enabled** for frontend integration
- **HTTP-only cookies** for JWT token storage

## User Roles

- **ADMIN**: Full access to all endpoints
- **OUTLET_MANAGER**: Standard user access (can be extended with specific permissions)

## Running the Application

1. **Build the project**:
   ```bash
   mvn clean install
   ```

2. **Run the application**:
   ```bash
   cd app
   mvn spring-boot:run
   ```

3. **Access the application**:
   - Base URL: `http://localhost:8080`
   - H2 Console: `http://localhost:8080/h2-console`
   - Health Check: `http://localhost:8080/api/health`

## Database Configuration

The application uses H2 in-memory database with the following configuration:
- URL: `jdbc:h2:mem:forkcast`
- Username: `sa`
- Password: `password`

## Sample API Usage

### Register a new user:
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@forkcast.com",
    "password": "password123",
    "firstName": "Admin",
    "lastName": "User",
    "role": "ADMIN"
  }'
```

### Login:
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@forkcast.com",
    "password": "password123"
  }'
```

### Health Check:
```bash
curl http://localhost:8080/api/health
```

## Development Notes

- JWT secret should be changed in production
- SSL/HTTPS should be enabled in production
- Database configuration should be updated for production use
- Logging levels can be adjusted in `application.properties`

## Technology Stack

- Spring Boot 3.5.5
- Jersey JAX-RS 3.1.3
- Spring Security
- Spring Data JPA
- H2 Database
- JWT (java-jwt 4.4.0)
- Lombok
- Maven
