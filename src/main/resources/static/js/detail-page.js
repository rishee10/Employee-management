import { fetchEmployee } from './api.js';

const params = new URLSearchParams(window.location.search);
const id = params.get('id');

function showAlert(message, type = 'danger') {
  const box = document.getElementById('alertBox');
  box.className = `alert alert-${type}`;
  box.textContent = message;
  box.classList.remove('d-none');
}

function formatSalary(value) {
  return new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR' }).format(value);
}

function renderDetail(employee) {
  document.getElementById('editLink').href = `/edit.html?id=${employee.id}`;
  const rows = [
    ['Employee ID', employee.employeeId],
    ['Full name', employee.fullName],
    ['Email', employee.email],
    ['Phone', employee.phoneNumber],
    ['Department', employee.department],
    ['Designation', employee.designation],
    ['Date of joining', employee.dateOfJoining],
    ['Salary', formatSalary(employee.salary)],
    ['Employment status', employee.employmentStatus],
  ];
  const list = document.getElementById('detailList');
  list.innerHTML = rows
    .map(
      ([label, value]) => `
      <dt class="col-sm-3">${label}</dt>
      <dd class="col-sm-9">${value}</dd>`
    )
    .join('');
}

if (!id) {
  showAlert('Missing employee id in URL.');
} else {
  if (params.get('updated') === '1') {
    showAlert('Employee updated successfully.', 'success');
  }
  fetchEmployee(id).then(renderDetail).catch((err) => showAlert(err.message));
}
