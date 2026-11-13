import { apiFetch } from './api'

export function getProductos({ criticos, antiguos } = {}) {
  const params = new URLSearchParams()
  if (criticos) params.set('criticos', 'true')
  if (antiguos) params.set('antiguos', 'true')
  const q = params.toString()
  return apiFetch(`/productos${q ? `?${q}` : ''}`)
}

export const createProducto = (body) =>
  apiFetch('/productos', { method: 'POST', body: JSON.stringify(body) })

export const updateProducto = (id, body) =>
  apiFetch(`/productos/${id}`, { method: 'PUT', body: JSON.stringify(body) })
