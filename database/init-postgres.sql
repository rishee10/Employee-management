-- PostgreSQL setup for employee-management (optional; JPA can also create schema)
CREATE DATABASE employee_db;

\c employee_db;

CREATE USER employee_user WITH ENCRYPTED PASSWORD 'employee_pass';
GRANT ALL PRIVILEGES ON DATABASE employee_db TO employee_user;

-- Run the application with: SPRING_PROFILES_ACTIVE=postgres
