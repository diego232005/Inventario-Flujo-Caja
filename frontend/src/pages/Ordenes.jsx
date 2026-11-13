import { useEffect, useState } from 'react'
import { getOrdenesSugeridas } from '../services/ordenService'
import { formatCop } from '../services/api'
import Alert from '../components/Alert'

export default function Ordenes() {
  const [items, setItems] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  const load = () => {
    setLoading(true)
    setError(null)
    getOrdenesSugeridas()
      .then(setItems)
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false))
  }

  useEffect(() => { load() }, [])

  const total = items.reduce((acc, o) => acc + Number(o.costoTotal || 0), 0)

  return (
    <>
      <h2 className="page-title">Órdenes de compra sugeridas</h2>
      <p className="page-sub">
        Cantidad = max(0, ideal − actual) cuando hay stock crítico o antigüedad &gt;90 con stock bajo ideal.
      </p>

      {error && <Alert type="error" onRetry={load}>{error}</Alert>}

      {loading ? (
        <p className="page-sub">Calculando sugerencias…</p>
      ) : items.length === 0 ? (
        <p className="empty">No hay órdenes sugeridas con las reglas actuales.</p>
      ) : (
        <>
          <p className="page-sub">Total estimado: <strong>{formatCop(total)}</strong> · {items.length} ítems</p>
          <div className="table-wrap">
            <table>
              <thead>
                <tr>
                  <th>SKU</th><th>Producto</th><th>Proveedor</th><th>Stock</th>
                  <th>Cant. sugerida</th><th>Costo unit.</th><th>Costo total</th>
                  <th>Lead</th><th>Calif.</th><th>Regla</th>
                </tr>
              </thead>
              <tbody>
                {items.map((o) => (
                  <tr key={o.productoId}>
                    <td>{o.sku}</td>
                    <td>{o.nombreProducto}</td>
                    <td>{o.proveedorNombre}</td>
                    <td className="num">{o.stockActual}/{o.stockIdeal}</td>
                    <td className="num">{o.cantidadSugerida}</td>
                    <td className="num">{formatCop(o.costoUnitario)}</td>
                    <td className="num">{formatCop(o.costoTotal)}</td>
                    <td className="num">{o.leadTimeDias}d</td>
                    <td className="num">{o.calificacionCosto}/5</td>
                    <td>{o.reglaAplicada}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </>
      )}
    </>
  )
}
