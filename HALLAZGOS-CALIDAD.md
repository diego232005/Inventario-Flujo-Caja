# Hallazgos de calidad (código generado / prototipo)

## Trazabilidad
- RF01–RF10 del PRD cubiertos por dashboard, productos, filtros, proveedores, sugerencias, seed y validaciones.

## Separación de responsabilidades
- Backend: controllers delgados, lógica en services, JPA en repositories.
- Frontend: páginas + servicios API separados.

## Validación
- Bean Validation en requests (`@NotBlank`, `@Min`, etc.).
- Validación adicional en formularios React.
- Errores mapeados a `ApiError` sin exponer stack traces al cliente.

## Seguridad / prototipo
- Sin autenticación (deliberado).
- CORS limitado a localhost Vite.
- Consola H2 habilitada solo para desarrollo.
- No hay secretos en el repositorio.

## Decisiones cuestionables / deuda técnica
1. H2 `create-drop` + seed: útil para demo, no para producción.
2. Capacidad de reorden simplificada (`flujoNeto - costoSugerencias`); con el seed actual puede ser negativa (sugerencias > flujo neto), lo cual es útil para discutir decisión de compra; no modela cuentas por pagar reales.
3. Sin OpenAPI/Swagger en esta versión (se probaron endpoints con HTTP directo).
4. Sin tests automatizados unitarios exhaustivos (smoke de arranque + matriz manual P01–P10).
5. `EAGER` en relación Producto→Proveedor aceptable para volumen seed; revisar con paginación real.
6. Spring Boot 4.1.2 (versión entregada por Initializr actual); documentada en README.

## Antes de un despliegue real
- Autenticación/autorización
- Base de datos persistente
- Auditoría de movimientos
- Pruebas automatizadas CI
- Deshabilitar H2 console
