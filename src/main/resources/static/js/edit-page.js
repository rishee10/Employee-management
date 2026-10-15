import { fetchEmployee } from './api.js';
import { bindFormSubmit, renderEmployeeForm } from './form.js';

const params = new URLSearchParams(window.location.search);
const id = params.get('id');

function showAlert(message, type = 'danger') {
  const box = document.getElementById('alertBox');
  box.className = `alert alert-${type}`;
  box.textContent = message;
  box.classList.remove('d-none');
}

if (!id) {
  showAlert('Missing employee id in URL.');
} else {
  fetchEmployee(id)
    .then((employee) => {
      const form = document.getElementById('employeeForm');
      renderEmployeeForm(form, { mode: 'edit', employee });
      bindFormSubmit(form, 'edit', id);
    })
    .catch((err) => showAlert(err.message));
}
