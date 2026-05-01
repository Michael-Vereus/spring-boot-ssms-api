# SSMS — Simple Storage Management System
**Spring Boot REST API**

A backend REST API for managing storage inventory — tracking items, storage locations, and stock movements through an immutable ledger model. Built on Spring Boot 4 with JWT-based stateless authentication.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.0.5 |
| Build Tool | Gradle |
| Security | Spring Security 7 + JWT (JJWT 0.12.6) |
| Persistence | Spring Data JPA + Hibernate 7 |
| Database | MySQL |
| Migration | Flyway |
| Validation | Jakarta Bean Validation |
| Documentation | SpringDoc OpenAPI 3.0.2 (Swagger UI) |
| Utilities | Lombok, Spring Actuator |

---

## Project Structure

```
src/main/java/com/ver/ssms/
├── controller/
│   ├── security/
│   │   └── UserController.java        # Auth endpoints (register, login)
│   ├── ItemController.java
│   ├── StorageController.java
│   └── StockController.java
├── service/
│   ├── auth/
│   │   └── AuthService.java           # Authentication logic + JWT issuance
│   ├── UserService.java               # UserDetailsService impl
│   ├── ItemService.java
│   ├── StorageService.java
│   └── StockService.java              # Ledger-based stock logic
├── repo/
│   ├── UserRepository.java
│   ├── ItemRepository.java
│   ├── StorageRepository.java
│   └── StockRepository.java           # Native SQL stock aggregation queries
├── model/
│   ├── UserEntity.java                # Implements UserDetails
│   ├── Item.java
│   ├── Storage.java
│   └── Stock.java                     # Immutable ledger entry
├── dto/
│   ├── BodyResponse.java              # Unified response wrapper
│   ├── auth/                          # LoginUser, RegisterUser
│   ├── item/incoming/                 # CreateItem, UpdateItem, DeleteItem
│   ├── storage/incoming/              # CreateStorage, UpdateStorage, DeleteStorage
│   └── stock/
│       ├── incoming/                  # CreateStock, UpdateStock
│       └── outrepo/                   # StockSummary, StockSummaryMapping
├── security/
│   └── SecurityConfig.java            # Filter chain, DaoAuthProvider, BCrypt
├── utility/
│   ├── JwtUtil.java                   # Token generation, validation, claims
│   ├── JwtAuthFilter.java             # OncePerRequestFilter JWT interceptor
│   ├── UserRole.java                  # USER, ADMIN
│   ├── TransactionType.java           # IN, OUT, TRANSFERRED
│   └── statuses/                      # StockRequestStatus, AuthRequestStatus
└── exception/
    ├── GlobalExceptionHandler.java    # @RestControllerAdvice
    └── UsernameAlreadyExistsException.java
```

---

## Getting Started

### Prerequisites

- Java 21
- MySQL 8+
- Gradle (or use the included `./gradlew` wrapper)

### Database Setup

Create a MySQL database named `ssms_api`:

```sql
CREATE DATABASE ssms_api;
```

Then configure your credentials in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ssms_api?serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Running the Application

```bash
./gradlew bootRun
```

The server starts on **port 8082** by default.

---

## Authentication

SSMS uses **JWT Bearer token** authentication with a stateless session policy.

Tokens expire after **15 minutes**.

> ⚠️ The `SECRET_KEY` in `JwtUtil.java` is hardcoded for development purposes. Move it to an environment variable before any production deployment.

### Flow

```
POST /ssms/api/auth/register  →  Create account
POST /ssms/api/auth/login     →  Receive JWT token
Authorization: Bearer <token> →  Access protected routes
```

---

## API Reference

All endpoints are prefixed with `/ssms/api`. Routes other than `/auth/login` and `/auth/register` require a valid `Authorization: Bearer <token>` header.

### Auth — `/ssms/api/auth`

| Method | Endpoint | Auth Required | Description |
|---|---|---|---|
| `POST` | `/register` | ❌ | Register a new user |
| `POST` | `/login` | ❌ | Login and receive a JWT token |

**Register Request Body:**
```json
{
  "username": "john_doe",
  "password": "secret123"
}
```

**Login Request Body:**
```json
{
  "username": "john_doe",
  "password": "secret123"
}
```

**Login Response:**
```json
{
  "status": true,
  "response": {
    "token": "<jwt_token>"
  }
}
```

---

### Items — `/ssms/api/item`

| Method | Endpoint | Auth Required | Description |
|---|---|---|---|
| `GET` | `/index` | ✅ | List all items |
| `GET` | `/search/{name}` | ✅ | Search item by name |
| `POST` | `/new` | ✅ | Create a new item |
| `PUT` | `/update` | ✅ | Update an existing item |
| `DELETE` | `/del` | ✅ | Delete an item by ID |

---

### Storage — `/ssms/api/storage`

| Method | Endpoint | Auth Required | Description |
|---|---|---|---|
| `GET` | `/index` | ✅ | List all storage locations |
| `GET` | `/search/{name}` | ✅ | Search storage by name |
| `POST` | `/new` | ✅ | Create a new storage location |
| `PUT` | `/update` | ✅ | Update a storage location |
| `DELETE` | `/del` | ✅ | Delete a storage location |

---

### Stock — `/ssms/api/stock`

| Method | Endpoint | Auth Required | Description |
|---|---|---|---|
| `GET` | `/index` | ✅ | Get current stock-on-hand (aggregated) |
| `POST` | `/new` | ✅ | Record a new stock transaction |
| `PUT` | `/edit` | ✅ | Update/transfer a stock entry |
| `DELETE` | `/delete` | ✅ | Zero out stock for given IDs |

---

## Stock Ledger Model

Stock is tracked as an **immutable transaction ledger** rather than a mutable quantity field. Each stock entry records a movement, and the current quantity on hand is derived via SQL aggregation.

### Transaction Types

| Type | Description |
|---|---|
| `IN` | Stock received into a storage location |
| `OUT` | Stock removed from a storage location |
| `TRANSFERRED` | Stock moved between items or storages |

### Quantity on Hand Formula

```sql
SUM(CASE WHEN type = 'IN'          THEN quantity ELSE 0 END) -
SUM(CASE WHEN type = 'OUT'         THEN quantity ELSE 0 END) -
SUM(CASE WHEN type = 'TRANSFERRED' THEN quantity ELSE 0 END)
```

This design ensures a complete audit trail — no record is ever overwritten.

---

## Response Structure

All endpoints return a unified `BodyResponse<T>` envelope:

```json
{
  "status": true,
  "response": {
    "result": { ...}
  }
}
```

On failure:
```json
{
  "status": false,
  "response": {
    "error": "Descriptive error message"
  }
}
```

---

## API Documentation

Swagger UI is available at:

```
http://localhost:8082/swagger-ui.html
```

---

## Roles

| Role | Description |
|---|---|
| `USER` | Default role assigned on registration |
| `ADMIN` | Elevated privileges (role-based access control to be expanded) |

---

## Notes & Known Issues

- **JWT Secret Key** — Currently hardcoded in `JwtUtil.java`. Must be externalised to environment configuration before production.
- **Flyway** — Migration is set to `baseline-on-migrate` mode. Schema migrations are not yet included; ensure the database schema is created manually before first run.
- **DDL** — `spring.jpa.hibernate.ddl-auto=none` means Hibernate will not auto-create tables. Manage schema changes manually or through Flyway migrations.
- **`StockService.updateStock`** — Contains a known note from the author to verify logic correctness; review before production use.

---

## Author

**Michael Vereus** — Information Systems Student & Backend Engineer