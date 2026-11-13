package com.inventario.flujo.service;

import com.inventario.flujo.dto.ProveedorRequest;
import com.inventario.flujo.model.Proveedor;
import com.inventario.flujo.repository.ProveedorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    @Transactional(readOnly = true)
    public List<Proveedor> listar() {
        return proveedorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Proveedor obtener(Long id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proveedor no encontrado"));
    }

    public Proveedor crear(ProveedorRequest req) {
        if (proveedorRepository.existsByNombreIgnoreCase(req.getNombre())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nombre de proveedor ya existe");
        }
        Proveedor p = new Proveedor();
        map(req, p);
        return proveedorRepository.save(p);
    }

    public Proveedor actualizar(Long id, ProveedorRequest req) {
        Proveedor p = obtener(id);
        proveedorRepository.findByNombreIgnoreCase(req.getNombre()).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Nombre de proveedor ya existe");
            }
        });
        map(req, p);
        return proveedorRepository.save(p);
    }

    private void map(ProveedorRequest req, Proveedor p) {
        p.setNombre(req.getNombre().trim());
        p.setContacto(req.getContacto().trim());
        p.setEmail(req.getEmail());
        p.setTelefono(req.getTelefono());
        p.setLeadTimeDias(req.getLeadTimeDias() == null ? 0 : req.getLeadTimeDias());
        p.setCalificacionCosto(req.getCalificacionCosto() == null ? 3 : req.getCalificacionCosto());
        p.setActivo(req.getActivo() == null || req.getActivo());
    }
}
