package com.inventario.flujo.dto;

import jakarta.validation.constraints.*;

public class ProveedorRequest {

    @NotBlank
    private String nombre;
    @NotBlank
    private String contacto;
    private String email;
    private String telefono;
    @Min(0)
    private Integer leadTimeDias = 0;
    @Min(1) @Max(5)
    private Integer calificacionCosto = 3;
    private Boolean activo = true;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public Integer getLeadTimeDias() { return leadTimeDias; }
    public void setLeadTimeDias(Integer leadTimeDias) { this.leadTimeDias = leadTimeDias; }
    public Integer getCalificacionCosto() { return calificacionCosto; }
    public void setCalificacionCosto(Integer calificacionCosto) { this.calificacionCosto = calificacionCosto; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
