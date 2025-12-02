# Ecoembes Central Server API — Prototype (Java 21, Spring Boot)

- No persistence (in-memory).
- Patterns: DTO, AppService, Facade, StateManagement.
- Swagger UI included.

## Run
```
./gradlew bootRun
```
Swagger: http://localhost:8080/swagger-ui/index.html

## Quick test
Login:
```
curl -X POST "http://localhost:8080/api/auth/login?email=alice@ecoembes.com&password=password"
```
Use token:
```
TOKEN=...
curl -H "Authorization: Bearer $TOKEN" http://localhost:8080/api/plants
```
