package com.Grupo1.TPIntegracionBackEnd.repository;

import com.Grupo1.TPIntegracionBackEnd.model.LineaDeVenta;
import com.Grupo1.TPIntegracionBackEnd.model.LineaDeVentaId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineaDeVentaRepository extends JpaRepository<LineaDeVenta, LineaDeVentaId> {
}
