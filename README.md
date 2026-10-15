# Employee Management System

Web application to manage employee records with full CRUD, search, filters, and pagination.

**Stack:** Java 17, Spring Boot 3, Spring Data JPA, PostgreSQL (production), HTML + CSS + Bootstrap.

## Prerequisites

- JDK 17+
- Maven 3.9+
- Docker Desktop for PostgreSQL + containerized app (Optional we can also do quick start to check)

## Quick start (H2 in-memory — no database install)

```bash
cd employee-management
mvn spring-boot:run
```

Open [http://localhost:8080](http://localhost:8080)

Sample employees are loaded for tests from `src/main/resources/data.sql`.

Run unit tests:

```bash
mvn test
```

## PostgreSQL (local)

1. Start PostgreSQL and create DB/user (see `database/init-postgres.sql`), or use Docker Compose below.
2. Run with profile `postgres`:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=postgres
```

Environment variables (defaults shown):

| Variable | Default |
|----------|---------|
| `DB_HOST` | `localhost` |
| `DB_PORT` | `5432` |
| `DB_NAME` | `employee_db` |
| `DB_USER` | `employee_user` |
| `DB_PASSWORD` | `your_password` |

## Docker Compose (PostgreSQL + app)

```bash
cd employee-management
docker compose up --build
```

App: [http://localhost:8080](http://localhost:8080)

## Features

| Requirement | Implementation |
|-------------|----------------|
| Employee fields | ID, name, email, phone, department, designation, DOJ, salary, status |
| CRUD | REST `/api/employees` + Bootstrap UI pages |
| Search | Name or email (`search` query param) |
| Filters | Department, employment status |
| Pagination | `page`, `size` query params |
| Delete confirmation | Bootstrap modal on list page |
| Unit tests | `EmployeeServiceTest` (JUnit) |
| Bonus | CSV export (`GET /api/employees/export`), Docker Compose | (Download all employees details)

## UI pages

| Page | URL |
|------|-----|
| Employee list | `/` or `/index.html` |
| Add employee | `/add.html` |
| Edit employee | `/edit.html?id={id}` |
| Employee details | `/detail.html?id={id}` |

## REST API summary

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/employees` | List (search, filter, paginate) |
| GET | `/api/employees/{id}` | Get one |
| POST | `/api/employees` | Create |
| PUT | `/api/employees/{id}` | Update |
| DELETE | `/api/employees/{id}` | Delete |
| GET | `/api/employees/departments` | Distinct departments |
| GET | `/api/employees/export` | CSV download |

## Project layout

```
employee-management/
├── src/main/java/com/kloudspot/employeemanagement/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── model/
│   └── dto/
├── src/main/resources/static/    # frontend partt
├── src/test/java/                # Service unit tests
├── database/init-postgres.sql
├── postman/
├── docker-compose.yml
└── Dockerfile
```

## screenshots
<img width="1917" height="977" alt="Screenshot 2026-10-15 193436" src="https://github.com/user-attachments/assets/0316ebbd-6d00-4fbd-81b3-3eb8adb388a8" />

<img width="1917" height="962" alt="Screenshot 2026-10-15 193536" src="https://github.com/user-attachments/assets/36fc6118-d217-4fec-b24a-2ab00405a04d" />

<img width="1917" height="968" alt="Screenshot 2026-10-15 193500" src="https://github.com/user-attachments/assets/f0f9b9ba-f817-44f3-ab9d-aaf3cc7a5ee3" />





