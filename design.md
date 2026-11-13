# Design System — Inventario y Flujo de Caja

## Dirección visual
Aplicación empresarial clara y sobria para almacén IT. Prioriza lectura rápida de KPI y tablas densas pero legibles. Evitar temas genéricos “AI purple”.

## Paleta
| Token | Hex | Uso |
|-------|-----|-----|
| `--bg` | `#0F172A` | Fondo app (slate oscuro) |
| `--surface` | `#1E293B` | Paneles / tablas |
| `--surface-2` | `#334155` | Hover / bordes suaves |
| `--text` | `#F8FAFC` | Texto principal |
| `--muted` | `#94A3B8` | Texto secundario |
| `--accent` | `#0EA5E9` | Acciones primarias (sky) |
| `--accent-2` | `#14B8A6` | Éxito / positivo (teal) |
| `--warn` | `#F59E0B` | Advertencia |
| `--danger` | `#EF4444` | Crítico / error |
| `--border` | `#475569` | Bordes |

## Tipografía
- Display / títulos: **Source Serif 4**
- UI / tablas: **IBM Plex Sans**
- Números KPI: tabular lining (`font-variant-numeric: tabular-nums`)

## Espaciado
Escala 4/8/12/16/24/32/48. Padding de página 24px desktop, 16px mobile.

## Componentes
- **Botón primario:** fondo `--accent`, radio 6px, altura 40px.
- **Botón secundario:** borde `--border`, fondo transparente.
- **Inputs:** fondo `--surface`, borde 1px, label arriba.
- **Tablas:** header sticky, zebra suave, alineación numérica a la derecha.
- **Badges:** Crítico (danger), >90 días (warn), OK (accent-2).
- **KPI cards:** no “card stack” decorativo; bloque plano con borde inferior accent.

## Estados
- Loading: skeleton o texto “Cargando…”
- Empty: mensaje + CTA contextual
- Error: banner con reintentar
- Confirmación: toast breve (no modal obligatorio en MVP)

## Layout
- Sidebar izquierda fija (Dashboard, Inventario, Proveedores, Órdenes)
- Contenido principal fluido
- En móvil: sidebar colapsable / top nav

## Accesibilidad
- Contraste AA en texto sobre `--bg`/`--surface`
- Focus visible en controles
- No depender solo del color para críticos (incluir etiqueta)
