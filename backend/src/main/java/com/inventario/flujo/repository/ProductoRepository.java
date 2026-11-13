package com.inventario.flujo.repository;

import com.inventario.flujo.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findBySkuIgnoreCase(String sku);
    boolean existsBySkuIgnoreCase(String sku);

    @Query("SELECT p FROM Producto p WHERE p.stockActual <= p.stockMinimo")
    List<Producto> findCriticos();

    @Query("SELECT p FROM Producto p WHERE p.diasEnInventario > 90")
    List<Producto> findAntiguos();
}
