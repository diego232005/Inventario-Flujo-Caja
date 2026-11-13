package com.inventario.flujo.dto;

import java.math.BigDecimal;

public class DashboardResponse {
    private long totalProductos;
    private long productosCriticos;
    private long productosAntiguos;
    private long totalProveedores;
    private BigDecimal capitalInmovilizado;
    private BigDecimal ingresosPeriodo;
    private BigDecimal gastosPeriodo;
    private BigDecimal flujoNeto;
    private BigDecimal costoOrdenesSugeridas;
    private BigDecimal capacidadReorden;
    private String periodo;

    public long getTotalProductos() { return totalProductos; }
    public void setTotalProductos(long totalProductos) { this.totalProductos = totalProductos; }
    public long getProductosCriticos() { return productosCriticos; }
    public void setProductosCriticos(long productosCriticos) { this.productosCriticos = productosCriticos; }
    public long getProductosAntiguos() { return productosAntiguos; }
    public void setProductosAntiguos(long productosAntiguos) { this.productosAntiguos = productosAntiguos; }
    public long getTotalProveedores() { return totalProveedores; }
    public void setTotalProveedores(long totalProveedores) { this.totalProveedores = totalProveedores; }
    public BigDecimal getCapitalInmovilizado() { return capitalInmovilizado; }
    public void setCapitalInmovilizado(BigDecimal capitalInmovilizado) { this.capitalInmovilizado = capitalInmovilizado; }
    public BigDecimal getIngresosPeriodo() { return ingresosPeriodo; }
    public void setIngresosPeriodo(BigDecimal ingresosPeriodo) { this.ingresosPeriodo = ingresosPeriodo; }
    public BigDecimal getGastosPeriodo() { return gastosPeriodo; }
    public void setGastosPeriodo(BigDecimal gastosPeriodo) { this.gastosPeriodo = gastosPeriodo; }
    public BigDecimal getFlujoNeto() { return flujoNeto; }
    public void setFlujoNeto(BigDecimal flujoNeto) { this.flujoNeto = flujoNeto; }
    public BigDecimal getCostoOrdenesSugeridas() { return costoOrdenesSugeridas; }
    public void setCostoOrdenesSugeridas(BigDecimal costoOrdenesSugeridas) { this.costoOrdenesSugeridas = costoOrdenesSugeridas; }
    public BigDecimal getCapacidadReorden() { return capacidadReorden; }
    public void setCapacidadReorden(BigDecimal capacidadReorden) { this.capacidadReorden = capacidadReorden; }
    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }
}
