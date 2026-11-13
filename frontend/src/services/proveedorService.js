import { apiFetch } from './api'

export const getProveedores = () => apiFetch('/proveedores')

export const createProveedor = (body) =>
  apiFetch('/proveedores', { method: 'POST', body: JSON.stringify(body) })

export const updateProveedor = (id, body) =>
  apiFetch(`/proveedores/${id}`, { method: 'PUT', body: JSON.stringify(body) })
