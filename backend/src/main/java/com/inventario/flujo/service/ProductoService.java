package com.inventario.flujo.service;

import com.inventario.flujo.dto.ProductoRequest;
import com.inventario.flujo.model.Producto;
import com.inventario.flujo.model.Proveedor;
import com.inventario.flujo.repository.ProductoRepository;
import com.inventario.flujo.repository.ProveedorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository;

    public ProductoService(ProductoRepository productoRepository, ProveedorRepository proveedorRepository) {
        this.productoRepository = productoRepository;
        this.proveedorRepository = proveedorRepository;
    }

    @Transactional(readOnly = true)
    public List<Producto> listar(Boolean criticos, Boolean antiguos) {
        if (Boolean.TRUE.equals(criticos)) {
            return productoRepository.findCriticos();
        }
        if (Boolean.TRUE.equals(antiguos)) {
            return productoRepository.findAntiguos();
        }
        return productoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Producto obtener(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    public Producto crear(ProductoRequest req) {
        if (productoRepository.existsBySkuIgnoreCase(req.getSku())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "SKU ya existe");
        }
        Producto p = new Producto();
        map(req, p);
        return productoRepository.save(p);
    }

    public Producto actualizar(Long id, ProductoRequest req) {
        Producto p = obtener(id);
        productoRepository.findBySkuIgnoreCase(req.getSku()).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "SKU ya existe");
            }
        });
        map(req, p);
        return productoRepository.save(p);
    }

    private void map(ProductoRequest req, Producto p) {
        Proveedor proveedor = proveedorRepository.findById(req.getProveedorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Proveedor no válido"));
        p.setSku(req.getSku().trim());
        p.setNombre(req.getNombre().trim());
        p.setMarca(req.getMarca().trim());
        p.setCategoria(req.getCategoria().trim());
        p.setSubcategoria(req.getSubcategoria());
        p.setCostoUnitario(req.getCostoUnitario());
        p.setPrecioVenta(req.getPrecioVenta());
        p.setStockActual(req.getStockActual());
        p.setStockMinimo(req.getStockMinimo());
        p.setStockIdeal(req.getStockIdeal());
        p.setDiasEnInventario(req.getDiasEnInventario());
        p.setProveedor(proveedor);
    }
}
