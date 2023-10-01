package com.factura.app.models.service;

import java.util.List;

import com.factura.app.models.entity.Empresa;

public interface IEmpresaService {
	public List<Empresa> findAll();

	public void save(Empresa factura);

	public Empresa findOne(Long id);

	public void delete(Long id);

	public List<Empresa> findByNombreEmpresa(String term);

}
