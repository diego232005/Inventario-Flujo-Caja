# Matriz de pruebas funcionales

**Proyecto:** Inventario y Flujo de Caja  
**Fecha:** 12/11/2026  
**Entorno:** Backend `:8080` + Frontend `:5173` · H2 in-memory

| ID | Prueba | Pasos | Resultado esperado | Resultado real | Estado | Evidencia / notas |
|----|--------|-------|--------------------|----------------|--------|-------------------|
| P01 | Abrir dashboard | Abrir `/` con backend arriba | KPI coherentes con `GET /api/dashboard` | KPI cargan desde API (capital, flujo, críticos, etc.) | APROBADA | `GET /api/dashboard` OK |
| P02 | Catálogo productos | Ir a Inventario | Tabla con registros backend | 22 productos seed visibles | APROBADA | `GET /api/productos` |
| P03 | Filtrar críticos | Clic en “Críticos” | Solo `stockActual ≤ stockMinimo` | Filtro `?criticos=true` retorna subconjunto correcto | APROBADA | Comparado con regla PRD |
| P04 | Filtrar >90 días | Clic en “>90 días” | Solo `diasEnInventario > 90` | Filtro `?antiguos=true` correcto | APROBADA | Incluye p.ej. SRV-DEL-009, HDD-WD-020 |
| P05 | Consultar proveedores | Ir a Proveedores | Listado con datos relevantes | 5 proveedores con lead time y calificación | APROBADA | `GET /api/proveedores` |
| P06 | Sugerencia de compra | Ir a Órdenes sugeridas | Recomendación por reglas, no texto estático | Cantidad y `reglaAplicada` calculadas en servicio | APROBADA | `GET /api/ordenes/sugeridas` |
| P07 | Error de API | Detener backend y recargar | Mensaje útil, UI no bloqueada | Alert con “Reintentar” | APROBADA | Manejo en páginas |
| P08 | Validar formularios | Crear producto/proveedor vacío | Errores de campos obligatorios | Validación cliente + `@Valid` servidor | APROBADA | Formularios Inventario/Proveedores |
| P09 | Recargar / reiniciar | Reiniciar backend | Datos H2 se reinicializan con seed | Comportamiento documentado (memoria) | APROBADA | DataLoader al arranque |
| P10 | Diseño adaptable | Reducir ventana | Legibilidad en móvil/desktop | Sidebar apila; tablas con scroll | APROBADA | CSS `@media` |

## Registro de fallos

Ningún fallo bloqueante abierto al cierre de esta matriz. Defectos menores / deuda: ver `HALLAZGOS-CALIDAD.md`.
