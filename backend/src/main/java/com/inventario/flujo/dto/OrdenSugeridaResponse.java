package com.inventario.flujo.dto;

import java.math.BigDecimal;

public class OrdenSugeridaResponse {
    private Long productoId;
    private String sku;
    private String nombreProducto;
    private Long proveedorId;
    private String proveedorNombre;
    private Integer stockActual;
    private Integer stockMinimo;
    private Integer stockIdeal;
    private Integer diasEnInventario;
    private Integer cantidadSugerida;
    private BigDecimal costoUnitario;
    private BigDecimal costoTotal;
    private String reglaAplicada;
    private Integer leadTimeDias;
    private Integer calificacionCosto;

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }
    public Long getProveedorId() { return proveedorId; }
    public void setProveedorId(Long proveedorId) { this.proveedorId = proveedorId; }
    public String getProveedorNombre() { return proveedorNombre; }
    public void setProveedorNombre(String proveedorNombre) { this.proveedorNombre = proveedorNombre; }
    public Integer getStockActual() { return stockActual; }
    public void setStockActual(Integer stockActual) { this.stockActual = stockActual; }
    public Integer getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(Integer stockMinimo) { this.stockMinimo = stockMinimo; }
    public Integer getStockIdeal() { return stockIdeal; }
    public void setStockIdeal(Integer stockIdeal) { this.stockIdeal = stockIdeal; }
    public Integer getDiasEnInventario() { return diasEnInventario; }
    public void setDiasEnInventario(Integer diasEnInventario) { this.diasEnInventario = diasEnInventario; }
    public Integer getCantidadSugerida() { return cantidadSugerida; }
    public void setCantidadSugerida(Integer cantidadSugerida) { this.cantidadSugerida = cantidadSugerida; }
    public BigDecimal getCostoUnitario() { return costoUnitario; }
    public void setCostoUnitario(BigDecimal costoUnitario) { this.costoUnitario = costoUnitario; }
    public BigDecimal getCostoTotal() { return costoTotal; }
    public void setCostoTotal(BigDecimal costoTotal) { this.costoTotal = costoTotal; }
    public String getReglaAplicada() { return reglaAplicada; }
    public void setReglaAplicada(String reglaAplicada) { this.reglaAplicada = reglaAplicada; }
    public Integer getLeadTimeDias() { return leadTimeDias; }
    public void setLeadTimeDias(Integer leadTimeDias) { this.leadTimeDias = leadTimeDias; }
    public Integer getCalificacionCosto() { return calificacionCosto; }
    public void setCalificacionCosto(Integer calificacionCosto) { this.calificacionCosto = calificacionCosto; }
}
