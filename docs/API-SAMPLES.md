# Sample API requests

Base URL: `http://localhost:8080`

## List employees (search + filters + pagination)

```http
GET /api/employees?page=0&size=10&search=priya&department=Engineering&status=ACTIVE&sortBy=fullName&sortDir=asc
```

## Get employee

```http
GET /api/employees/1
```

## Create employee

```http
POST /api/employees
Content-Type: application/json

{
  "employeeId": "EMP2025-100",
  "fullName": "Test User",
  "email": "test.user@kloudspot.com",
  "phoneNumber": "+91-9000000000",
  "department": "Engineering",
  "designation": "Developer",
  "dateOfJoining": "2025-01-10",
  "salary": 600000,
  "employmentStatus": "ACTIVE"
}
```

## Update employee

```http
PUT /api/employees/1
Content-Type: application/json
```

(Same JSON body as create.)

## Delete employee

```http
DELETE /api/employees/1
```

## Export CSV

```http
GET /api/employees/export?department=Engineering&status=ACTIVE
```
