# PowerFind API Gateway

This project is a Spring Cloud Gateway microservice, built as part of my PowerFind system to demonstrate advanced Java
and Spring development skills.

### API Key Authentication and Role-Based Access Control

This gateway uses an API key filter to secure access to internal services.
You must include the HTTP header X-API-KEY in every request.

Header name: X-API-KEY

Default value: user-key (predefined in the database via Liquibase)

If the header is missing or invalid, the gateway will respond with 403 Forbidden.

In addition to authentication, the gateway enforces role-based access control:

Available Roles

1) admin — full access to all API operations (key: admin-key-abc-456)

2) user — restricted access (key: user-key-xyz-123)

The user role is automatically resolved from the API key and injected into the downstream request as the X-USER-ROLE
header.

Example Usage:

```
GET http://localhost:8081/api/v1/location
X-API-KEY: user-key
```

To perform privileged operations (like saving a location), use the admin key:

```
POST http://localhost:8081/api/v1/location
X-API-KEY: admin-key
```