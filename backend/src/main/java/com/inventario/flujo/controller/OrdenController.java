package com.inventario.flujo.controller;

import com.inventario.flujo.dto.OrdenSugeridaResponse;
import com.inventario.flujo.service.OrdenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {

    private final OrdenService ordenService;

    public OrdenController(OrdenService ordenService) {
        this.ordenService = ordenService;
    }

    @GetMapping("/sugeridas")
    public List<OrdenSugeridaResponse> sugeridas() {
        return ordenService.sugeridas();
    }
}
