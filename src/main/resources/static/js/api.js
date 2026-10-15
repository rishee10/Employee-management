const API_BASE = '/api/employees';

async function apiRequest(url, options = {}) {
  const response = await fetch(url, {
    headers: { 'Content-Type': 'application/json', ...(options.headers || {}) },
    ...options,
  });
  if (response.status === 204) {
    return null;
  }
  const data = await response.json().catch(() => ({}));
  if (!response.ok) {
    const message = data.error || data.message || 'Request failed';
    throw new Error(message);
  }
  return data;
}

export async function fetchEmployees(params) {
  const query = new URLSearchParams(params);
  return apiRequest(`${API_BASE}?${query.toString()}`);
}

export async function fetchEmployee(id) {
  return apiRequest(`${API_BASE}/${id}`);
}

export async function createEmployee(payload) {
  return apiRequest(API_BASE, { method: 'POST', body: JSON.stringify(payload) });
}

export async function updateEmployee(id, payload) {
  return apiRequest(`${API_BASE}/${id}`, { method: 'PUT', body: JSON.stringify(payload) });
}

export async function deleteEmployee(id) {
  return apiRequest(`${API_BASE}/${id}`, { method: 'DELETE' });
}

export async function fetchDepartments() {
  return apiRequest(`${API_BASE}/departments`);
}

export function exportCsvUrl(params) {
  const query = new URLSearchParams(params);
  return `${API_BASE}/export?${query.toString()}`;
}
