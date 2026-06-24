package com.Grupo1.TPIntegracionBackEnd.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CrearVentaRequest {

    private LocalDateTime fecha;
    private String cliente;
    private List<CrearLineaVentaRequest> productos;

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public List<CrearLineaVentaRequest> getProductos() {
        return productos;
    }

    public void setProductos(List<CrearLineaVentaRequest> productos) {
        this.productos = productos;
    }
}
