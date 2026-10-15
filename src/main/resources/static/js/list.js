import { deleteEmployee, exportCsvUrl, fetchDepartments, fetchEmployees } from './api.js';

const state = { page: 0, size: 10, deleteId: null };
const deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));

function showAlert(message, type = 'danger') {
  const box = document.getElementById('alertBox');
  box.className = `alert alert-${type}`;
  box.textContent = message;
  box.classList.remove('d-none');
}

function statusBadge(status) {
  const cls = status === 'ACTIVE' ? 'status-badge-active' : 'status-badge-inactive';
  return `<span class="badge ${cls}">${status}</span>`;
}

function currentFilters() {
  return {
    search: document.getElementById('search').value.trim(),
    department: document.getElementById('department').value,
    status: document.getElementById('status').value,
  };
}

async function loadDepartments() {
  const departments = await fetchDepartments();
  const select = document.getElementById('department');
  departments.forEach((dept) => {
    const option = document.createElement('option');
    option.value = dept;
    option.textContent = dept;
    select.appendChild(option);
  });
}

function renderPagination(pageData) {
  const pagination = document.getElementById('pagination');
  pagination.innerHTML = '';
  const totalPages = pageData.totalPages;
  const current = pageData.number;

  const addItem = (label, page, disabled = false, active = false) => {
    const li = document.createElement('li');
    li.className = `page-item${disabled ? ' disabled' : ''}${active ? ' active' : ''}`;
    const btn = document.createElement('button');
    btn.type = 'button';
    btn.className = 'page-link';
    btn.textContent = label;
    btn.disabled = disabled;
    btn.addEventListener('click', () => {
      state.page = page;
      loadEmployees();
    });
    li.appendChild(btn);
    pagination.appendChild(li);
  };

  addItem('Prev', current - 1, current === 0);
  for (let i = 0; i < totalPages; i++) {
    addItem(String(i + 1), i, false, i === current);
  }
  addItem('Next', current + 1, current >= totalPages - 1);
}

function renderTable(pageData) {
  const tbody = document.getElementById('employeeTableBody');
  tbody.innerHTML = '';
  const empty = document.getElementById('emptyState');
  if (!pageData.content.length) {
    empty.classList.remove('d-none');
    return;
  }
  empty.classList.add('d-none');

  pageData.content.forEach((emp) => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>${emp.employeeId}</td>
      <td>${emp.fullName}</td>
      <td>${emp.email}</td>
      <td>${emp.department}</td>
      <td>${statusBadge(emp.employmentStatus)}</td>
      <td class="text-end table-actions">
        <a class="btn btn-sm btn-outline-primary" href="/detail.html?id=${emp.id}">View</a>
        <a class="btn btn-sm btn-outline-secondary" href="/edit.html?id=${emp.id}">Edit</a>
        <button class="btn btn-sm btn-outline-danger" data-id="${emp.id}" data-name="${emp.fullName}">Delete</button>
      </td>`;
    tbody.appendChild(tr);
  });

  tbody.querySelectorAll('button[data-id]').forEach((btn) => {
    btn.addEventListener('click', () => {
      state.deleteId = btn.dataset.id;
      document.getElementById('deleteEmployeeName').textContent = btn.dataset.name;
      deleteModal.show();
    });
  });
}

async function loadEmployees() {
  try {
    const filters = currentFilters();
    const data = await fetchEmployees({
      ...filters,
      page: state.page,
      size: state.size,
      sortBy: 'fullName',
      sortDir: 'asc',
    });
    renderTable(data);
    renderPagination(data);
    document.getElementById('pageInfo').textContent =
      `Showing page ${data.number + 1} of ${Math.max(data.totalPages, 1)} (${data.totalElements} employees)`;
  } catch (err) {
    showAlert(err.message);
  }
}

document.getElementById('filterForm').addEventListener('submit', (e) => {
  e.preventDefault();
  state.page = 0;
  loadEmployees();
});

document.getElementById('confirmDeleteBtn').addEventListener('click', async () => {
  try {
    await deleteEmployee(state.deleteId);
    deleteModal.hide();
    await loadEmployees();
    showAlert('Employee deleted successfully.', 'success');
  } catch (err) {
    showAlert(err.message);
  }
});

document.getElementById('exportBtn').addEventListener('click', () => {
  const filters = currentFilters();
  window.location.href = exportCsvUrl(filters);
});

const urlParams = new URLSearchParams(window.location.search);
if (urlParams.get('created') === '1') {
  showAlert('Employee created successfully.', 'success');
}

loadDepartments().then(loadEmployees).catch((err) => showAlert(err.message));
