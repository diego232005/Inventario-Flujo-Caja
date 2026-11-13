package com.inventario.flujo.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ProductoRequest {

    @NotBlank
    private String sku;
    @NotBlank
    private String nombre;
    @NotBlank
    private String marca;
    @NotBlank
    private String categoria;
    private String subcategoria;
    @NotNull @DecimalMin("0.0")
    private BigDecimal costoUnitario;
    @NotNull @DecimalMin("0.0")
    private BigDecimal precioVenta;
    @NotNull @Min(0)
    private Integer stockActual;
    @NotNull @Min(0)
    private Integer stockMinimo;
    @NotNull @Min(0)
    private Integer stockIdeal;
    @NotNull @Min(0)
    private Integer diasEnInventario;
    @NotNull
    private Long proveedorId;

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getSubcategoria() { return subcategoria; }
    public void setSubcategoria(String subcategoria) { this.subcategoria = subcategoria; }
    public BigDecimal getCostoUnitario() { return costoUnitario; }
    public void setCostoUnitario(BigDecimal costoUnitario) { this.costoUnitario = costoUnitario; }
    public BigDecimal getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(BigDecimal precioVenta) { this.precioVenta = precioVenta; }
    public Integer getStockActual() { return stockActual; }
    public void setStockActual(Integer stockActual) { this.stockActual = stockActual; }
    public Integer getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(Integer stockMinimo) { this.stockMinimo = stockMinimo; }
    public Integer getStockIdeal() { return stockIdeal; }
    public void setStockIdeal(Integer stockIdeal) { this.stockIdeal = stockIdeal; }
    public Integer getDiasEnInventario() { return diasEnInventario; }
    public void setDiasEnInventario(Integer diasEnInventario) { this.diasEnInventario = diasEnInventario; }
    public Long getProveedorId() { return proveedorId; }
    public void setProveedorId(Long proveedorId) { this.proveedorId = proveedorId; }
}
