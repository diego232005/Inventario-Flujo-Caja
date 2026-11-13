# PRD — Inventario y Flujo de Caja (Suministros Informáticos)

**Producto:** Prototipo web de gestión de inventario, proveedores y cierre mensual  
**Versión:** 1.0 (MVP)  
**Fecha:** 12 de noviembre de 2026  
**Asignatura:** Fundamentos de Sistemas de Información  
**Estado:** Revisado y aprobado para implementación

---

## 1. Problema

Como dueño de un negocio que vende equipos de cómputo (computadores, portátiles, software, componentes, servidores, UPS y accesorios), al hacer el cierre de mes necesito ver gastos, ingresos, inventario y compromisos con proveedores para decidir si realizo un nuevo pedido sin comprometer el flujo de caja ni quedar en quiebre de stock.

Hoy la información está dispersa (hojas de cálculo, mensajes y memoria), lo que alarga el cierre mensual, oculta productos críticos u obsoletos y dificulta comparar proveedores antes de comprar.

## 2. Objetivos del producto

### 2.1 Objetivo general
Entregar un prototipo web que consolide inventario, proveedores, sugerencias de reabastecimiento y un resumen ejecutivo de salud financiera del almacén para apoyar la decisión de compra al cierre de mes.

### 2.2 Objetivos específicos
- Reducir el tiempo de consolidación del cierre mensual.
- Detectar productos con stock crítico, baja rotación u obsolescencia (>90 días).
- Comparar proveedores y generar órdenes de compra sugeridas con reglas explícitas.
- Mostrar capacidad de reorden relativa al capital inmovilizado y flujo básico.

## 3. Usuarios y comportamiento

| Rol | Comportamiento principal | Necesidad |
|-----|--------------------------|-----------|
| Dueño / gerente del almacén | Revisa dashboard al cierre de mes; decide pedidos | Visión rápida de KPI, stock y capacidad de compra |
| Encargado de inventario | Consulta catálogo, filtra críticos y antigüedad | Listados filtrables y datos actualizados |
| Encargado de compras | Compara proveedores y revisa sugerencias | Costos, lead time y recomendaciones accionables |

**Sin autenticación en el MVP:** cualquier usuario local accede a todas las pantallas (limitación deliberada del prototipo).

## 4. Espacio de la solución (alcance)

### 4.1 Incluido (MVP)
1. **Tablero de cierre mensual y salud financiera** — métricas de inventario, capital inmovilizado, ingresos/gastos simulados del período, capacidad de reorden, conteo de críticos.
2. **Inventario y catálogo tecnológico** — listado de productos con SKU, marca, categoría, costos, precio, stock actual/mínimo/ideal, días en inventario, proveedor.
3. **Filtros de riesgo** — productos críticos (stock ≤ mínimo) y productos con más de 90 días en inventario.
4. **Proveedores** — listado y datos relevantes (contacto, lead time, calificación de costo).
5. **Órdenes de compra sugeridas** — recomendaciones calculadas por reglas de stock, ideal y rotación/días, no texto estático.
6. **CRUD básico** de productos y proveedores vía API/UI (crear/editar esenciales para demo).

### 4.2 Excluido (explícito)
- Facturación electrónica / DIAN.
- Contabilidad completa.
- E-commerce B2C y pasarelas de pago.
- Nómina y prestaciones.
- Autenticación y autorización de usuarios.
- Persistencia productiva (se usa H2 en memoria).

## 5. Métricas de éxito (referencia)

| Métrica | Indicador verificable en el prototipo |
|---------|----------------------------------------|
| Tiempo de cierre | Dashboard consolida KPI en una sola vista alimentada por API |
| Quiebres de stock | Filtro de críticos identifica stock ≤ mínimo |
| Precisión de flujo | Capacidad de reorden = f(caja estimada, capital inmovilizado, pedidos sugeridos) |
| Adopción de recomendaciones | UI muestra origen de cada sugerencia (regla aplicada) |

## 6. Features clave (priorizadas)

### Must-have (MVP)
- F1 Dashboard con KPI desde backend
- F2 Catálogo de productos (≥20 registros seed)
- F3 Filtro críticos y filtro >90 días
- F4 Listado de proveedores
- F5 Generación de sugerencias de compra
- F6 Estados de carga, vacío y error en UI
- F7 Validación de formularios (cliente + servidor)

### Should-have
- F8 Comparación simple de costo entre proveedores del mismo tipo de producto
- F9 Edición de stock mínimo/ideal

### Could-have (fuera del MVP actual)
- Histórico de cierres mensuales
- Exportación CSV/PDF
- Autenticación

## 7. Requisitos funcionales

| ID | Requisito | Prioridad | Criterio de aceptación |
|----|-----------|-----------|------------------------|
| RF01 | El sistema muestra un dashboard con resumen ejecutivo | Must | Al abrir `/` se ven KPI coherentes con `GET /api/dashboard` |
| RF02 | Listar productos del catálogo | Must | Tabla con datos de `GET /api/productos` |
| RF03 | Filtrar productos críticos | Must | Solo productos con `stockActual <= stockMinimo` |
| RF04 | Filtrar productos >90 días | Must | Solo productos con `diasEnInventario > 90` |
| RF05 | Listar proveedores | Must | Datos de `GET /api/proveedores` |
| RF06 | Generar sugerencias de OC | Must | Cantidad sugerida = max(0, stockIdeal - stockActual) cuando stock ≤ mínimo o días>90 con stock bajo ideal |
| RF07 | Crear/actualizar producto | Must | Validación de campos obligatorios; HTTP 201/200 |
| RF08 | Crear/actualizar proveedor | Must | Validación de nombre y contacto |
| RF09 | Manejo de error/vacío | Must | Mensaje útil sin bloquear la UI |
| RF10 | Datos de prueba al arranque | Must | ≥20 productos y ≥5 proveedores en H2 |

## 8. Requisitos no funcionales

| ID | Requisito | Criterio comprobable |
|----|-----------|----------------------|
| RNF01 | Stack | Backend Spring Boot API REST; Frontend React; BD H2 in-memory |
| RNF02 | Rendimiento local | Listados < 2 s en máquina de desarrollo con seed |
| RNF03 | Usabilidad | Jerarquía clara de KPI; tablas legibles; filtros visibles |
| RNF04 | Accesibilidad básica | Contraste razonable; labels en formularios; navegación por teclado en controles principales |
| RNF05 | Mantenibilidad | Capas controller/service/repository; servicios API separados en frontend |
| RNF06 | Seguridad del prototipo | Sin secretos en repo; sin auth; no apto para datos reales |
| RNF07 | Documentación | README con versiones e instrucciones reproducibles |

## 9. Flujos principales

### Flujo A — Cierre mensual
1. Usuario abre Dashboard.
2. Sistema consulta métricas agregadas.
3. Usuario ve capital inmovilizado, críticos, capacidad de reorden.
4. Decide si ir a sugerencias de compra o a inventario.

### Flujo B — Detectar riesgo de inventario
1. Usuario abre Inventario.
2. Aplica filtro “Críticos” o “>90 días”.
3. Revisa filas resultantes.
4. Opcionalmente navega a Órdenes sugeridas.

### Flujo C — Reabastecimiento
1. Usuario abre Proveedores / Órdenes sugeridas.
2. Sistema calcula sugerencias con reglas de stock.
3. Usuario compara costo/lead time del proveedor asociado.
4. Confirma visualmente la sugerencia (sin emisión real a proveedor en MVP).

## 10. Reglas de negocio

1. **Crítico:** `stockActual <= stockMinimo`.
2. **Obsolescencia / baja rotación visual:** `diasEnInventario > 90`.
3. **Capital inmovilizado:** Σ (`stockActual * costoUnitario`).
4. **Cantidad sugerida de compra:** si crítico o (días>90 y stock < ideal): `max(0, stockIdeal - stockActual)`; en otro caso 0.
5. **Capacidad de reorden (simplificada):** `cajaEstimadaDisponible - costoTotalSugerencias` (valores seed del período).
6. Moneda: COP (pesos colombianos) en formato de visualización.

## 11. Fuera de alcance — recordatorio
Cualquier módulo excluido en §4.2 requiere actualizar este PRD, la especificación técnica y el plan de trabajo antes de implementarse.

## 12. Entregables asociados
- `PRD.md` (este documento)
- `design.md` + `mockups/stitch/`
- `SPEC-TECNICA.md`
- `backend/` + `frontend/`
- `README.md`
- `MATRIZ-PRUEBAS.md`
