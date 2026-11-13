import { useEffect, useState } from 'react'
import { getProveedores, createProveedor } from '../services/proveedorService'
import Alert from '../components/Alert'

const empty = {
  nombre: '', contacto: '', email: '', telefono: '',
  leadTimeDias: '5', calificacionCosto: '3', activo: true,
}

export default function Proveedores() {
  const [items, setItems] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [form, setForm] = useState(empty)
  const [formErrors, setFormErrors] = useState({})
  const [showForm, setShowForm] = useState(false)
  const [saving, setSaving] = useState(false)

  const load = () => {
    setLoading(true)
    setError(null)
    getProveedores()
      .then(setItems)
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false))
  }

  useEffect(() => { load() }, [])

  const validate = () => {
    const e = {}
    if (!form.nombre.trim()) e.nombre = 'Obligatorio'
    if (!form.contacto.trim()) e.contacto = 'Obligatorio'
    if (Number(form.leadTimeDias) < 0) e.leadTimeDias = 'Inválido'
    const cal = Number(form.calificacionCosto)
    if (cal < 1 || cal > 5) e.calificacionCosto = '1 a 5'
    setFormErrors(e)
    return Object.keys(e).length === 0
  }

  const onSubmit = async (ev) => {
    ev.preventDefault()
    if (!validate()) return
    setSaving(true)
    try {
      await createProveedor({
        ...form,
        leadTimeDias: Number(form.leadTimeDias),
        calificacionCosto: Number(form.calificacionCosto),
      })
      setForm(empty)
      setShowForm(false)
      load()
    } catch (err) {
      setError(err.message)
    } finally {
      setSaving(false)
    }
  }

  const set = (k) => (e) => setForm((f) => ({ ...f, [k]: e.target.value }))

  return (
    <>
      <h2 className="page-title">Proveedores</h2>
      <p className="page-sub">Comparación de lead time y calificación de costo</p>

      <div className="toolbar">
        <button type="button" className="btn primary" onClick={() => setShowForm((s) => !s)}>
          {showForm ? 'Cerrar formulario' : 'Nuevo proveedor'}
        </button>
      </div>

      {error && <Alert type="error" onRetry={load}>{error}</Alert>}

      {showForm && (
        <form className="form-grid" onSubmit={onSubmit} noValidate>
          <label>Nombre<input value={form.nombre} onChange={set('nombre')} />
            {formErrors.nombre && <span className="field-error">{formErrors.nombre}</span>}</label>
          <label>Contacto<input value={form.contacto} onChange={set('contacto')} />
            {formErrors.contacto && <span className="field-error">{formErrors.contacto}</span>}</label>
          <label>Email<input value={form.email} onChange={set('email')} /></label>
          <label>Teléfono<input value={form.telefono} onChange={set('telefono')} /></label>
          <label>Lead time (días)<input value={form.leadTimeDias} onChange={set('leadTimeDias')} />
            {formErrors.leadTimeDias && <span className="field-error">{formErrors.leadTimeDias}</span>}</label>
          <label>Calif. costo (1-5)<input value={form.calificacionCosto} onChange={set('calificacionCosto')} />
            {formErrors.calificacionCosto && <span className="field-error">{formErrors.calificacionCosto}</span>}</label>
          <div style={{ alignSelf: 'end' }}>
            <button type="submit" className="btn primary" disabled={saving}>
              {saving ? 'Guardando…' : 'Crear proveedor'}
            </button>
          </div>
        </form>
      )}

      {loading ? (
        <p className="page-sub">Cargando proveedores…</p>
      ) : items.length === 0 ? (
        <p className="empty">No hay proveedores registrados.</p>
      ) : (
        <div className="table-wrap">
          <table>
            <thead>
              <tr>
                <th>Nombre</th><th>Contacto</th><th>Email</th><th>Teléfono</th>
                <th>Lead time</th><th>Calif. costo</th><th>Estado</th>
              </tr>
            </thead>
            <tbody>
              {items.map((p) => (
                <tr key={p.id}>
                  <td>{p.nombre}</td>
                  <td>{p.contacto}</td>
                  <td>{p.email}</td>
                  <td>{p.telefono}</td>
                  <td className="num">{p.leadTimeDias} días</td>
                  <td className="num">{p.calificacionCosto}/5</td>
                  <td>{p.activo ? 'Activo' : 'Inactivo'}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </>
  )
}
