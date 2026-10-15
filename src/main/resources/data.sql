INSERT INTO employees (employee_id, full_name, email, phone_number, department, designation, date_of_joining, salary, employment_status)
SELECT 'EMP2024-001', 'Priya Sharma', 'priya.sharma@kloudspot.com', '+91-9876543210', 'Engineering', 'Senior Developer', '2022-03-15', 850000.00, 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM employees WHERE email = 'priya.sharma@kloudspot.com');

INSERT INTO employees (employee_id, full_name, email, phone_number, department, designation, date_of_joining, salary, employment_status)
SELECT 'EMP2023-014', 'Rahul Mehta', 'rahul.mehta@kloudspot.com', '+91-9123456780', 'Human Resources', 'HR Manager', '2021-07-01', 720000.00, 'ACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM employees WHERE email = 'rahul.mehta@kloudspot.com');

INSERT INTO employees (employee_id, full_name, email, phone_number, department, designation, date_of_joining, salary, employment_status)
SELECT 'EMP2020-008', 'Ananya Iyer', 'ananya.iyer@kloudspot.com', '+91-9988776655', 'Finance', 'Financial Analyst', '2020-11-20', 680000.00, 'INACTIVE'
WHERE NOT EXISTS (SELECT 1 FROM employees WHERE email = 'ananya.iyer@kloudspot.com');

