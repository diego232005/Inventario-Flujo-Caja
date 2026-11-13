import { useEffect, useState } from 'react'
import { getProductos, createProducto } from '../services/productoService'
import { getProveedores } from '../services/proveedorService'
import { formatCop } from '../services/api'
import Alert from '../components/Alert'
import StatusBadge from '../components/StatusBadge'

const emptyForm = {
  sku: '', nombre: '', marca: '', categoria: '', subcategoria: '',
  costoUnitario: '', precioVenta: '', stockActual: '', stockMinimo: '',
  stockIdeal: '', diasEnInventario: '', proveedorId: '',
}

export default function Inventario() {
  const [items, setItems] = useState([])
  const [proveedores, setProveedores] = useState([])
  const [filtro, setFiltro] = useState('todos')
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [form, setForm] = useState(emptyForm)
  const [formErrors, setFormErrors] = useState({})
  const [saving, setSaving] = useState(false)
  const [showForm, setShowForm] = useState(false)

  const load = () => {
    setLoading(true)
    setError(null)
    const opts = filtro === 'criticos' ? { criticos: true }
      : filtro === 'antiguos' ? { antiguos: true } : {}
    Promise.all([getProductos(opts), getProveedores()])
      .then(([prods, provs]) => {
        setItems(prods)
        setProveedores(provs)
        if (!form.proveedorId && provs[0]) {
          setForm((f) => ({ ...f, proveedorId: String(provs[0].id) }))
        }
      })
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false))
  }

  useEffect(() => { load() }, [filtro])

  const validate = () => {
    const e = {}
    if (!form.sku.trim()) e.sku = 'Obligatorio'
    if (!form.nombre.trim()) e.nombre = 'Obligatorio'
    if (!form.marca.trim()) e.marca = 'Obligatorio'
    if (!form.categoria.trim()) e.categoria = 'Obligatorio'
    if (form.costoUnitario === '' || Number(form.costoUnitario) < 0) e.costoUnitario = 'Inválido'
    if (form.precioVenta === '' || Number(form.precioVenta) < 0) e.precioVenta = 'Inválido'
    ;['stockActual', 'stockMinimo', 'stockIdeal', 'diasEnInventario'].forEach((k) => {
      if (form[k] === '' || Number(form[k]) < 0) e[k] = 'Inválido'
    })
    if (!form.proveedorId) e.proveedorId = 'Obligatorio'
    setFormErrors(e)
    return Object.keys(e).length === 0
  }

  const onSubmit = async (ev) => {
    ev.preventDefault()
    if (!validate()) return
    setSaving(true)
    setError(null)
    try {
      await createProducto({
        ...form,
        costoUnitario: Number(form.costoUnitario),
        precioVenta: Number(form.precioVenta),
        stockActual: Number(form.stockActual),
        stockMinimo: Number(form.stockMinimo),
        stockIdeal: Number(form.stockIdeal),
        diasEnInventario: Number(form.diasEnInventario),
        proveedorId: Number(form.proveedorId),
      })
      setForm({ ...emptyForm, proveedorId: form.proveedorId })
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
      <h2 className="page-title">Catálogo tecnológico</h2>
      <p className="page-sub">Inventario con filtros de riesgo</p>

      <div className="toolbar">
        <button type="button" className={`btn${filtro === 'todos' ? ' active' : ''}`} onClick={() => setFiltro('todos')}>Todos</button>
        <button type="button" className={`btn${filtro === 'criticos' ? ' active' : ''}`} onClick={() => setFiltro('criticos')}>Críticos</button>
        <button type="button" className={`btn${filtro === 'antiguos' ? ' active' : ''}`} onClick={() => setFiltro('antiguos')}>&gt;90 días</button>
        <button type="button" className="btn primary" onClick={() => setShowForm((s) => !s)}>
          {showForm ? 'Cerrar formulario' : 'Nuevo producto'}
        </button>
      </div>

      {error && <Alert type="error" onRetry={load}>{error}</Alert>}

      {showForm && (
        <form className="form-grid" onSubmit={onSubmit} noValidate>
          {[
            ['sku', 'SKU'], ['nombre', 'Nombre'], ['marca', 'Marca'], ['categoria', 'Categoría'],
            ['subcategoria', 'Subcategoría'], ['costoUnitario', 'Costo'], ['precioVenta', 'Precio'],
            ['stockActual', 'Stock actual'], ['stockMinimo', 'Stock mínimo'], ['stockIdeal', 'Stock ideal'],
            ['diasEnInventario', 'Días en inventario'],
          ].map(([k, label]) => (
            <label key={k}>
              {label}
              <input value={form[k]} onChange={set(k)} />
              {formErrors[k] && <span className="field-error">{formErrors[k]}</span>}
            </label>
          ))}
          <label>
            Proveedor
            <select value={form.proveedorId} onChange={set('proveedorId')}>
              <option value="">Seleccione…</option>
              {proveedores.map((p) => (
                <option key={p.id} value={p.id}>{p.nombre}</option>
              ))}
            </select>
            {formErrors.proveedorId && <span className="field-error">{formErrors.proveedorId}</span>}
          </label>
          <div style={{ alignSelf: 'end' }}>
            <button type="submit" className="btn primary" disabled={saving}>
              {saving ? 'Guardando…' : 'Crear producto'}
            </button>
          </div>
        </form>
      )}

      {loading ? (
        <p className="page-sub">Cargando productos…</p>
      ) : items.length === 0 ? (
        <p className="empty">No hay productos para este filtro.</p>
      ) : (
        <div className="table-wrap">
          <table>
            <thead>
              <tr>
                <th>SKU</th><th>Producto</th><th>Marca</th><th>Categoría</th>
                <th>Stock</th><th>Mín/Ideal</th><th>Días</th><th>Costo</th>
                <th>Proveedor</th><th>Estado</th>
              </tr>
            </thead>
            <tbody>
              {items.map((p) => {
                const critico = p.stockActual <= p.stockMinimo
                const antiguo = p.diasEnInventario > 90
                return (
                  <tr key={p.id}>
                    <td>{p.sku}</td>
                    <td>{p.nombre}</td>
                    <td>{p.marca}</td>
                    <td>{p.categoria}</td>
                    <td className="num">{p.stockActual}</td>
                    <td className="num">{p.stockMinimo} / {p.stockIdeal}</td>
                    <td className="num">{p.diasEnInventario}</td>
                    <td className="num">{formatCop(p.costoUnitario)}</td>
                    <td>{p.proveedor?.nombre}</td>
                    <td><StatusBadge critico={critico} antiguo={antiguo} /></td>
                  </tr>
                )
              })}
            </tbody>
          </table>
        </div>
      )}
    </>
  )
}
