import { useEffect, useState } from 'react'
import { getDashboard } from '../services/dashboardService'
import { formatCop } from '../services/api'
import Alert from '../components/Alert'

export default function Dashboard() {
  const [data, setData] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  const load = () => {
    setLoading(true)
    setError(null)
    getDashboard()
      .then(setData)
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false))
  }

  useEffect(() => { load() }, [])

  if (loading) return <p className="page-sub">Cargando dashboard…</p>
  if (error) return <Alert type="error" onRetry={load}>{error}</Alert>
  if (!data) return <p className="empty">Sin datos de dashboard.</p>

  return (
    <>
      <h2 className="page-title">Cierre mensual</h2>
      <p className="page-sub">Resumen ejecutivo · período {data.periodo}</p>
      <div className="kpi-grid">
        <div className="kpi"><span>Capital inmovilizado</span><strong>{formatCop(data.capitalInmovilizado)}</strong></div>
        <div className="kpi ok"><span>Flujo neto</span><strong>{formatCop(data.flujoNeto)}</strong></div>
        <div className="kpi"><span>Ingresos</span><strong>{formatCop(data.ingresosPeriodo)}</strong></div>
        <div className="kpi"><span>Gastos</span><strong>{formatCop(data.gastosPeriodo)}</strong></div>
        <div className="kpi danger"><span>Productos críticos</span><strong>{data.productosCriticos}</strong></div>
        <div className="kpi warn"><span>&gt;90 días</span><strong>{data.productosAntiguos}</strong></div>
        <div className="kpi"><span>Capacidad de reorden</span><strong>{formatCop(data.capacidadReorden)}</strong></div>
        <div className="kpi"><span>Costo OC sugeridas</span><strong>{formatCop(data.costoOrdenesSugeridas)}</strong></div>
        <div className="kpi"><span>Productos / Proveedores</span><strong>{data.totalProductos} / {data.totalProveedores}</strong></div>
      </div>
    </>
  )
}
