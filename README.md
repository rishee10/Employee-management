# Employee Management System

Web application for administrators to manage employee records with full CRUD, search, filters, and pagination.

**Stack:** Java 17, Spring Boot 3, Spring Data JPA, PostgreSQL (production) / H2 (local default), HTML + CSS + Bootstrap 5.

## Prerequisites

- JDK 17+
- Maven 3.9+
- (Optional) Docker Desktop for PostgreSQL + containerized app

## Quick start (H2 in-memory — no database install)

```bash
cd employee-management
mvn spring-boot:run
```

Open [http://localhost:8080](http://localhost:8080)

Sample employees are loaded from `src/main/resources/data.sql`.

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
| `DB_PASSWORD` | `employee_pass` |

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
| Unit tests | `EmployeeServiceTest` (JUnit 5 + Mockito) |
| API docs | `docs/API-SAMPLES.md`, `postman/Employee-Management.postman_collection.json` |
| Bonus | CSV export (`GET /api/employees/export`), Docker Compose |

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
├── src/main/resources/static/    # Bootstrap frontend
├── src/test/java/                # Service unit tests
├── database/init-postgres.sql
├── postman/
├── docker-compose.yml
└── Dockerfile
```

## Demonstration / screenshots

For submission, capture:

1. Employee list with search/filters and pagination
2. Add employee form
3. Employee details page
4. Delete confirmation modal
5. (Optional) Postman response for `GET /api/employees`

See `docs/DEMO.md` for a short recording checklist.

## License

Kloudspot India Private Limited — candidate technical assessment.
