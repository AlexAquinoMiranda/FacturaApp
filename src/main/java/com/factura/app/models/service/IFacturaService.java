package com.factura.app.models.service;

import java.util.List;

import com.factura.app.models.entity.Factura;

public interface IFacturaService {
	public List<Factura> findAll();

	public void save(Factura factura);

	public Factura findOne(Long id);

	public void delete(Long id);

	public List<Factura> findByNombreFactura(String term);

}
