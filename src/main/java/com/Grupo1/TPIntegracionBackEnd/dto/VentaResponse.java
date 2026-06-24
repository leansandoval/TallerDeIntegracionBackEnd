package com.Grupo1.TPIntegracionBackEnd.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class VentaResponse {

    private Integer id;
    private Date fecha;
    private String cliente;
    private BigDecimal total;
    private Boolean rechazada;
    private List<LineaVentaResponse> productos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Boolean getRechazada() {
        return rechazada;
    }

    public void setRechazada(Boolean rechazada) {
        this.rechazada = rechazada;
    }

    public List<LineaVentaResponse> getProductos() {
        return productos;
    }

    public void setProductos(List<LineaVentaResponse> productos) {
        this.productos = productos;
    }
}
