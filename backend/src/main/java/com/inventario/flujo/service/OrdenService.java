package com.inventario.flujo.service;

import com.inventario.flujo.dto.OrdenSugeridaResponse;
import com.inventario.flujo.model.Producto;
import com.inventario.flujo.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrdenService {

    private final ProductoRepository productoRepository;

    public OrdenService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<OrdenSugeridaResponse> sugeridas() {
        List<OrdenSugeridaResponse> result = new ArrayList<>();
        for (Producto p : productoRepository.findAll()) {
            String regla = null;
            boolean critico = p.isCritico();
            boolean antiguoBajoIdeal = p.isAntiguo() && p.getStockActual() < p.getStockIdeal();
            if (critico) {
                regla = "Stock crítico (actual ≤ mínimo)";
            } else if (antiguoBajoIdeal) {
                regla = "Antigüedad >90 días y stock bajo ideal";
            }
            if (regla == null) {
                continue;
            }
            int cantidad = Math.max(0, p.getStockIdeal() - p.getStockActual());
            if (cantidad == 0) {
                continue;
            }
            OrdenSugeridaResponse o = new OrdenSugeridaResponse();
            o.setProductoId(p.getId());
            o.setSku(p.getSku());
            o.setNombreProducto(p.getNombre());
            o.setProveedorId(p.getProveedor().getId());
            o.setProveedorNombre(p.getProveedor().getNombre());
            o.setStockActual(p.getStockActual());
            o.setStockMinimo(p.getStockMinimo());
            o.setStockIdeal(p.getStockIdeal());
            o.setDiasEnInventario(p.getDiasEnInventario());
            o.setCantidadSugerida(cantidad);
            o.setCostoUnitario(p.getCostoUnitario());
            o.setCostoTotal(p.getCostoUnitario().multiply(BigDecimal.valueOf(cantidad)));
            o.setReglaAplicada(regla);
            o.setLeadTimeDias(p.getProveedor().getLeadTimeDias());
            o.setCalificacionCosto(p.getProveedor().getCalificacionCosto());
            result.add(o);
        }
        return result;
    }

    public BigDecimal costoTotalSugerencias() {
        return sugeridas().stream()
                .map(OrdenSugeridaResponse::getCostoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
