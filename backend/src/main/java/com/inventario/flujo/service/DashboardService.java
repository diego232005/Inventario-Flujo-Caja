package com.inventario.flujo.service;

import com.inventario.flujo.dto.DashboardResponse;
import com.inventario.flujo.model.Producto;
import com.inventario.flujo.model.TipoMovimiento;
import com.inventario.flujo.repository.MovimientoCajaRepository;
import com.inventario.flujo.repository.ProductoRepository;
import com.inventario.flujo.repository.ProveedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class DashboardService {

    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository;
    private final MovimientoCajaRepository movimientoCajaRepository;
    private final OrdenService ordenService;

    public DashboardService(ProductoRepository productoRepository,
                            ProveedorRepository proveedorRepository,
                            MovimientoCajaRepository movimientoCajaRepository,
                            OrdenService ordenService) {
        this.productoRepository = productoRepository;
        this.proveedorRepository = proveedorRepository;
        this.movimientoCajaRepository = movimientoCajaRepository;
        this.ordenService = ordenService;
    }

    public DashboardResponse resumen() {
        List<Producto> productos = productoRepository.findAll();
        BigDecimal capital = productos.stream()
                .map(p -> p.getCostoUnitario().multiply(BigDecimal.valueOf(p.getStockActual())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal ingresos = movimientoCajaRepository.sumByTipo(TipoMovimiento.INGRESO);
        BigDecimal gastos = movimientoCajaRepository.sumByTipo(TipoMovimiento.GASTO);
        if (ingresos == null) ingresos = BigDecimal.ZERO;
        if (gastos == null) gastos = BigDecimal.ZERO;
        BigDecimal flujoNeto = ingresos.subtract(gastos);
        BigDecimal costoSugeridas = ordenService.costoTotalSugerencias();
        // Capacidad de reorden simplificada: flujo neto disponible menos costo de sugerencias
        BigDecimal capacidad = flujoNeto.subtract(costoSugeridas);

        DashboardResponse d = new DashboardResponse();
        d.setTotalProductos(productos.size());
        d.setProductosCriticos(productos.stream().filter(Producto::isCritico).count());
        d.setProductosAntiguos(productos.stream().filter(Producto::isAntiguo).count());
        d.setTotalProveedores(proveedorRepository.count());
        d.setCapitalInmovilizado(capital);
        d.setIngresosPeriodo(ingresos);
        d.setGastosPeriodo(gastos);
        d.setFlujoNeto(flujoNeto);
        d.setCostoOrdenesSugeridas(costoSugeridas);
        d.setCapacidadReorden(capacidad);
        d.setPeriodo(YearMonth.now().toString());
        return d;
    }
}
