# Especificación Técnica — Inventario y Flujo de Caja

**Versión:** 1.0  
**Fecha:** 12/11/2026  
**Referencias:** `PRD.md`, `design.md`, `mockups/stitch/`

---

## 1. Arquitectura general

```
[ React (Vite) :5173 ] --HTTP/JSON--> [ Spring Boot REST :8080 ]
                                              |
                                         [ H2 in-memory ]
```

### Responsabilidades
| Componente | Responsabilidad |
|------------|-----------------|
| Frontend React | Vistas, filtros, formularios, consumo API, estados UI |
| Controllers | Exponer REST, validar entrada, mapear HTTP |
| Services | Reglas de negocio (críticos, sugerencias, dashboard) |
| Repositories | Persistencia JPA / consultas |
| H2 | Almacenamiento volátil de desarrollo con seed |

Sin autenticación en el MVP.

## 2. Stack y versiones registradas

| Tecnología | Versión objetivo |
|------------|------------------|
| Java | 17 |
| Spring Boot | 4.1.2 |
| Spring Data JPA | (BOM Spring Boot) |
| H2 | (BOM Spring Boot) |
| Maven Wrapper | incluido |
| Node.js | 24.x (verificado en entorno) |
| React | 18.3.x |
| Vite | 5.x |
| React Router | 6.x |

## 3. Modelo de datos

### 3.1 Proveedor (`proveedores`)
| Campo | Tipo | Restricción |
|-------|------|-------------|
| id | Long | PK auto |
| nombre | String | not null, unique |
| contacto | String | not null |
| email | String | |
| telefono | String | |
| leadTimeDias | Integer | >= 0 |
| calificacionCosto | Integer | 1–5 |
| activo | Boolean | default true |

### 3.2 Producto (`productos`)
| Campo | Tipo | Restricción |
|-------|------|-------------|
| id | Long | PK auto |
| sku | String | not null, unique |
| nombre | String | not null |
| marca | String | not null |
| categoria | String | not null |
| subcategoria | String | |
| costoUnitario | BigDecimal | >= 0 |
| precioVenta | BigDecimal | >= 0 |
| stockActual | Integer | >= 0 |
| stockMinimo | Integer | >= 0 |
| stockIdeal | Integer | >= 0 |
| diasEnInventario | Integer | >= 0 |
| proveedor | ManyToOne | not null |

### 3.3 MovimientoCaja (`movimientos_caja`) — soporte dashboard
| Campo | Tipo | Notas |
|-------|------|-------|
| id | Long | PK |
| tipo | ENUM INGRESO/GASTO | |
| concepto | String | |
| monto | BigDecimal | |
| fecha | LocalDate | período demo |

### Relaciones
- Un Proveedor tiene muchos Productos.
- Producto.proveedor obligatorio.

## 4. Capas backend

Paquete base: `com.inventario.flujo`

- `controller` — ProductoController, ProveedorController, DashboardController, OrdenController
- `service` — lógica RF03–RF06
- `repository` — JpaRepository + queries derivadas
- `model` / `dto` / `config` (CORS, DataLoader seed)

## 5. API REST

Base: `http://localhost:8080/api`

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/productos` | Listar; query `criticos=true`, `antiguos=true` |
| GET | `/productos/{id}` | Detalle |
| POST | `/productos` | Crear |
| PUT | `/productos/{id}` | Actualizar |
| GET | `/proveedores` | Listar |
| GET | `/proveedores/{id}` | Detalle |
| POST | `/proveedores` | Crear |
| PUT | `/proveedores/{id}` | Actualizar |
| GET | `/dashboard` | KPI agregados |
| GET | `/ordenes/sugeridas` | Sugerencias de compra |
| GET | `/actuator/health` | Salud (si habilitado) |

### Formato de error
```json
{ "timestamp": "...", "status": 400, "error": "Bad Request", "message": "...", "path": "..." }
```

### CORS
Origen permitido: `http://localhost:5173` (y 5174 por si Vite cambia puerto).

## 6. Frontend

### Estructura
```
frontend/src/
  pages/ Dashboard, Inventario, Proveedores, Ordenes
  components/ Layout, KpiStrip, DataTable, StatusBadge, Alert
  services/ api.js, productoService, proveedorService, dashboardService, ordenService
  styles/ tokens.css, app.css
```

### Vistas alineadas a mockups
1. Dashboard — KPI + resumen
2. Inventario — tabla + filtros críticos/>90
3. Proveedores — listado/gestión
4. Órdenes sugeridas — tabla calculada

## 7. H2 y datos iniciales
- `spring.datasource.url=jdbc:h2:mem:inventario;DB_CLOSE_DELAY=-1`
- Consola H2: `/h2-console` (solo desarrollo)
- `DataLoader`: ≥5 proveedores, ≥20 productos, movimientos de caja del mes demo
- Al reiniciar el backend, los datos se recrean (memoria)

## 8. Plan de pruebas
Ver `MATRIZ-PRUEBAS.md` (P01–P10). Criterio de finalización:
- Backend arranca sin error
- Frontend consume API real
- Filtros y sugerencias cumplen reglas del PRD
- Documentación completa

## 9. Fases de implementación
1. Documentos PRD / design / mockups / SPEC
2. Backend entidades + seed + endpoints
3. Frontend páginas + servicios
4. Pruebas funcionales P01–P10
5. Revisión de calidad y README

## 10. Criterios de finalización
- [x] PRD y SPEC coherentes
- [x] Mockups en repo
- [x] Prototipo ejecutable local
- [x] Matriz de pruebas
- [x] Listo para versionar con Git
