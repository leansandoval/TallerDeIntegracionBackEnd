package com.Grupo1.TPIntegracionBackEnd.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Grupo1.TPIntegracionBackEnd.dto.CrearLineaVentaRequest;
import com.Grupo1.TPIntegracionBackEnd.dto.CrearVentaRequest;
import com.Grupo1.TPIntegracionBackEnd.dto.LineaVentaResponse;
import com.Grupo1.TPIntegracionBackEnd.dto.VentaResponse;
import com.Grupo1.TPIntegracionBackEnd.model.LineaDeVenta;
import com.Grupo1.TPIntegracionBackEnd.model.Producto;
import com.Grupo1.TPIntegracionBackEnd.model.Venta;
import com.Grupo1.TPIntegracionBackEnd.repository.ProductoRepository;
import com.Grupo1.TPIntegracionBackEnd.repository.VentaRepository;

@Service
public class VentaService {

	@Autowired
	private VentaRepository ventaRepository;

	@Autowired
	private ProductoRepository productoRepository;

	public Venta getVenta(Integer idventa) {
		return ventaRepository.getById(idventa);
	}

	public List<Venta> getAllVentas() {
		return ventaRepository.findAll();
	}

	public Venta saveVenta(Venta venta) {
		return ventaRepository.save(venta);
	}

	@Transactional
	public VentaResponse registrarVenta(CrearVentaRequest request) {
		validarVenta(request);

		Venta venta = new Venta();
		venta.setCliente(request.getCliente().trim());
		venta.setFecha(toDate(request.getFecha()));
		venta.setRechazada(false);

		BigDecimal total = BigDecimal.ZERO;
		List<LineaDeVenta> lineas = new ArrayList<>();

		for (CrearLineaVentaRequest lineaRequest : request.getProductos()) {
			Producto producto = obtenerProductoVigente(lineaRequest.getCodigoProducto());
			Integer cantidad = lineaRequest.getCantidad();

			if (cantidad == null || cantidad <= 0) {
				throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
			}
			if (producto.getStock() == null || producto.getStock() < cantidad) {
				throw new IllegalArgumentException("Stock insuficiente para el producto " + producto.getCodigo() + ".");
			}

			BigDecimal precioUnitario = producto.getPrecio() == null ? BigDecimal.ZERO : producto.getPrecio();
			BigDecimal subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));

			producto.setStock(producto.getStock() - cantidad);

			LineaDeVenta linea = new LineaDeVenta();
			linea.setVenta(venta);
			linea.setProducto(producto);
			linea.setCantidad(cantidad);
			linea.setPrecioUnitario(precioUnitario);
			linea.setSubtotal(subtotal);
			lineas.add(linea);

			total = total.add(subtotal);
		}

		venta.setProductos(lineas);
		venta.setTotal(total);

		Venta ventaGuardada = ventaRepository.save(venta);
		return toResponse(ventaGuardada);
	}

	public List<Venta> getEntreFechas(LocalDateTime atStartOfDay, LocalDateTime atStartOfDay2) {
		return ventaRepository.findByFechaBetween(atStartOfDay, atStartOfDay2);
	}

	public Optional<Venta> getVentaById(Integer codigo) {
		return ventaRepository.findById(codigo);
	}

	private void validarVenta(CrearVentaRequest request) {
		if (request == null) {
			throw new IllegalArgumentException("La venta es obligatoria.");
		}
		if (request.getCliente() == null || request.getCliente().trim().isEmpty()) {
			throw new IllegalArgumentException("El cliente es obligatorio.");
		}
		if (request.getProductos() == null || request.getProductos().isEmpty()) {
			throw new IllegalArgumentException("La venta debe tener al menos un producto.");
		}
	}

	private Producto obtenerProductoVigente(String codigoProducto) {
		if (codigoProducto == null || codigoProducto.trim().isEmpty()) {
			throw new IllegalArgumentException("El codigo de producto es obligatorio.");
		}

		Producto producto = productoRepository.findById(codigoProducto.trim())
				.orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + codigoProducto + "."));

		if (Boolean.FALSE.equals(producto.getActivo())) {
			throw new IllegalArgumentException("El producto " + producto.getCodigo() + " no esta activo.");
		}

		return producto;
	}

	private Date toDate(LocalDateTime fecha) {
		LocalDateTime fechaVenta = fecha == null ? LocalDateTime.now() : fecha;
		return Date.from(fechaVenta.atZone(ZoneId.systemDefault()).toInstant());
	}

	private VentaResponse toResponse(Venta venta) {
		VentaResponse response = new VentaResponse();
		response.setId(venta.getId());
		response.setFecha(venta.getFecha());
		response.setCliente(venta.getCliente());
		response.setTotal(venta.getTotal());
		response.setRechazada(venta.getRechazada());
		response.setProductos(venta.getProductos().stream()
				.map(this::toLineaResponse)
				.collect(Collectors.toList()));
		return response;
	}

	private LineaVentaResponse toLineaResponse(LineaDeVenta linea) {
		LineaVentaResponse response = new LineaVentaResponse();
		response.setId(linea.getId());
		response.setCodigoProducto(linea.getProducto().getCodigo());
		response.setDescripcionProducto(linea.getProducto().getDescripcion());
		response.setCantidad(linea.getCantidad());
		response.setPrecioUnitario(linea.getPrecioUnitario());
		response.setSubtotal(linea.getSubtotal());
		return response;
	}
}
