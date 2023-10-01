package com.factura.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.factura.app.models.dao.IFacturaDao;
import com.factura.app.models.entity.Factura;

@Service
public class FacturaServiceImpl implements IFacturaService {

	@Autowired
	private IFacturaDao facturaDao;

	@Override
	@Transactional(readOnly = true)
	public List<Factura> findAll() {
		return (List<Factura>) this.facturaDao.findAll();
	}

	@Override
	@Transactional
	public void save(Factura factura) {
		facturaDao.save(factura);
	}

	@Override
	@Transactional(readOnly = true)
	public Factura findOne(Long id) {
		return facturaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		this.facturaDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Factura> findByNombreFactura(String term) {
		// TODO Auto-generated method stub
		return null;
	}

}
