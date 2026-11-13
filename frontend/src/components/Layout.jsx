import { NavLink, Outlet } from 'react-router-dom'

const links = [
  { to: '/', label: 'Dashboard', end: true },
  { to: '/inventario', label: 'Inventario' },
  { to: '/proveedores', label: 'Proveedores' },
  { to: '/ordenes', label: 'Órdenes sugeridas' },
]

export default function Layout() {
  return (
    <div className="app-shell">
      <aside className="sidebar">
        <h1 className="brand">Almacén IT</h1>
        <nav>
          {links.map((l) => (
            <NavLink
              key={l.to}
              to={l.to}
              end={l.end}
              className={({ isActive }) => `nav-link${isActive ? ' active' : ''}`}
            >
              {l.label}
            </NavLink>
          ))}
        </nav>
      </aside>
      <main className="content">
        <Outlet />
      </main>
    </div>
  )
}
