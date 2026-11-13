package com.inventario.flujo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String sku;

    @NotBlank
    @Column(nullable = false)
    private String nombre;

    @NotBlank
    private String marca;

    @NotBlank
    private String categoria;

    private String subcategoria;

    @NotNull
    @DecimalMin("0.0")
    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal costoUnitario;

    @NotNull
    @DecimalMin("0.0")
    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal precioVenta;

    @NotNull
    @Min(0)
    private Integer stockActual;

    @NotNull
    @Min(0)
    private Integer stockMinimo;

    @NotNull
    @Min(0)
    private Integer stockIdeal;

    @NotNull
    @Min(0)
    private Integer diasEnInventario;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    public boolean isCritico() {
        return stockActual != null && stockMinimo != null && stockActual <= stockMinimo;
    }

    public boolean isAntiguo() {
        return diasEnInventario != null && diasEnInventario > 90;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }
}
