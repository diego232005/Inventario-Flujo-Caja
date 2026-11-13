import { Routes, Route } from 'react-router-dom'
import Layout from './components/Layout'
import Dashboard from './pages/Dashboard'
import Inventario from './pages/Inventario'
import Proveedores from './pages/Proveedores'
import Ordenes from './pages/Ordenes'

export default function App() {
  return (
    <Routes>
      <Route element={<Layout />}>
        <Route index element={<Dashboard />} />
        <Route path="inventario" element={<Inventario />} />
        <Route path="proveedores" element={<Proveedores />} />
        <Route path="ordenes" element={<Ordenes />} />
      </Route>
    </Routes>
  )
}
