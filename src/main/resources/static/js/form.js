import { createEmployee, updateEmployee } from './api.js';

export function renderEmployeeForm(form, { mode, employee } = {}) {
  const isEdit = mode === 'edit';
  form.innerHTML = `
    <div class="row g-3">
      <div class="col-md-4">
        <label class="form-label" for="employeeId">Employee ID</label>
        <input type="text" class="form-control" id="employeeId" maxlength="20"
          placeholder="Auto-generated if left blank" ${isEdit ? 'readonly' : ''}>
      </div>
      <div class="col-md-8">
        <label class="form-label" for="fullName">Full name *</label>
        <input type="text" class="form-control" id="fullName" required maxlength="150">
      </div>
      <div class="col-md-6">
        <label class="form-label" for="email">Email *</label>
        <input type="email" class="form-control" id="email" required maxlength="255">
      </div>
      <div class="col-md-6">
        <label class="form-label" for="phoneNumber">Phone number *</label>
        <input type="tel" class="form-control" id="phoneNumber" required maxlength="20">
      </div>
      <div class="col-md-6">
        <label class="form-label" for="department">Department *</label>
        <input type="text" class="form-control" id="department" required maxlength="100">
      </div>
      <div class="col-md-6">
        <label class="form-label" for="designation">Designation *</label>
        <input type="text" class="form-control" id="designation" required maxlength="100">
      </div>
      <div class="col-md-4">
        <label class="form-label" for="dateOfJoining">Date of joining *</label>
        <input type="date" class="form-control" id="dateOfJoining" required>
      </div>
      <div class="col-md-4">
        <label class="form-label" for="salary">Salary (INR) *</label>
        <input type="number" class="form-control" id="salary" required min="1" step="0.01">
      </div>
      <div class="col-md-4">
        <label class="form-label" for="employmentStatus">Employment status *</label>
        <select class="form-select" id="employmentStatus" required>
          <option value="ACTIVE">Active</option>
          <option value="INACTIVE">Inactive</option>
        </select>
      </div>
    </div>
    <div class="mt-4 d-flex gap-2">
      <button type="submit" class="btn btn-primary">${isEdit ? 'Save changes' : 'Create employee'}</button>
      <a href="/" class="btn btn-outline-secondary">Cancel</a>
    </div>`;

  if (employee) {
    form.querySelector('#employeeId').value = employee.employeeId || '';
    form.querySelector('#fullName').value = employee.fullName || '';
    form.querySelector('#email').value = employee.email || '';
    form.querySelector('#phoneNumber').value = employee.phoneNumber || '';
    form.querySelector('#department').value = employee.department || '';
    form.querySelector('#designation').value = employee.designation || '';
    form.querySelector('#dateOfJoining').value = employee.dateOfJoining || '';
    form.querySelector('#salary').value = employee.salary ?? '';
    form.querySelector('#employmentStatus').value = employee.employmentStatus || 'ACTIVE';
  }
}

function readPayload(form) {
  const employeeId = form.querySelector('#employeeId').value.trim();
  return {
    employeeId: employeeId || null,
    fullName: form.querySelector('#fullName').value.trim(),
    email: form.querySelector('#email').value.trim(),
    phoneNumber: form.querySelector('#phoneNumber').value.trim(),
    department: form.querySelector('#department').value.trim(),
    designation: form.querySelector('#designation').value.trim(),
    dateOfJoining: form.querySelector('#dateOfJoining').value,
    salary: Number(form.querySelector('#salary').value),
    employmentStatus: form.querySelector('#employmentStatus').value,
  };
}

function showAlert(message, type = 'danger') {
  const box = document.getElementById('alertBox');
  box.className = `alert alert-${type}`;
  box.textContent = message;
  box.classList.remove('d-none');
}

export function bindFormSubmit(form, mode, employeeId) {
  form.addEventListener('submit', async (e) => {
    e.preventDefault();
    if (!form.checkValidity()) {
      form.classList.add('was-validated');
      return;
    }
    try {
      const payload = readPayload(form);
      if (mode === 'create') {
        await createEmployee(payload);
        window.location.href = '/?created=1';
      } else {
        await updateEmployee(employeeId, payload);
        window.location.href = `/detail.html?id=${employeeId}&updated=1`;
      }
    } catch (err) {
      showAlert(err.message);
    }
  });
}
