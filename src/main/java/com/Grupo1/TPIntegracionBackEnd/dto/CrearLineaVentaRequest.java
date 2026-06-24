package com.Grupo1.TPIntegracionBackEnd.dto;

public class CrearLineaVentaRequest {

    private String codigoProducto;
    private Integer cantidad;

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
