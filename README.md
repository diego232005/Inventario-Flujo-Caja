# Inventario y Flujo de Caja — Prototipo MVP

Prototipo web para gestión de inventario, proveedores, órdenes sugeridas y cierre mensual de un almacén de suministros informáticos.

## Estructura

```
inventario-flujo-caja/
├── PRD.md
├── design.md
├── SPEC-TECNICA.md
├── MATRIZ-PRUEBAS.md
├── HALLAZGOS-CALIDAD.md
├── mockups/stitch/
├── backend/          # Spring Boot + H2
└── frontend/         # React + Vite
```

## Versiones verificadas en el entorno de desarrollo

| Componente | Versión |
|------------|---------|
| Java JDK | 17.0.12 |
| Spring Boot | 4.1.2 |
| Maven Wrapper | 3.10.0 |
| Node.js | 24.19.0 |
| npm | 11.17.0 |
| React | 18.3.x |
| Vite | 5.4.x |

## Requisitos previos

- JDK 17+
- Node.js 18+ y npm
- Navegador moderno

## Ejecución

### 1. Backend (puerto 8080)

```bash
cd backend
# Windows
set JAVA_HOME=C:\Program Files\Java\jdk-17
mvnw.cmd spring-boot:run

# Linux/macOS
export JAVA_HOME=...
./mvnw spring-boot:run
```

- API base: `http://localhost:8080/api`
- Consola H2 (solo desarrollo): `http://localhost:8080/h2-console`  
  JDBC URL: `jdbc:h2:mem:inventario` · usuario `sa` · sin contraseña

Al reiniciar el backend, los datos seed se recrean (H2 en memoria).

### 2. Frontend (puerto 5173)

```bash
cd frontend
npm install
npm run dev
```

Abrir la URL que muestre Vite (normalmente `http://localhost:5173`).

## Endpoints principales

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/dashboard` | KPI de cierre |
| GET | `/api/productos` | Catálogo (`?criticos=true`, `?antiguos=true`) |
| POST | `/api/productos` | Crear producto |
| GET | `/api/proveedores` | Proveedores |
| POST | `/api/proveedores` | Crear proveedor |
| GET | `/api/ordenes/sugeridas` | Sugerencias de compra |

## Datos de prueba

- 5 proveedores
- 22 productos (incluye críticos y >90 días)
- Movimientos de caja del período demo

## Limitaciones del prototipo

- Sin autenticación/autorización
- H2 en memoria (no persistente)
- No apto para datos reales ni producción

## Documentación del laboratorio

Ver `PRD.md`, `SPEC-TECNICA.md`, `mockups/stitch/`, `MATRIZ-PRUEBAS.md` y `HALLAZGOS-CALIDAD.md`.
