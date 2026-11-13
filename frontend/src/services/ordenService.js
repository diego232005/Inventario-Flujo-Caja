import { apiFetch } from './api'

export const getOrdenesSugeridas = () => apiFetch('/ordenes/sugeridas')
