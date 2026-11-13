package com.inventario.flujo.repository;

import com.inventario.flujo.model.MovimientoCaja;
import com.inventario.flujo.model.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface MovimientoCajaRepository extends JpaRepository<MovimientoCaja, Long> {

    @Query("SELECT COALESCE(SUM(m.monto), 0) FROM MovimientoCaja m WHERE m.tipo = :tipo")
    BigDecimal sumByTipo(@Param("tipo") TipoMovimiento tipo);
}
