package com.factura.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.factura.app.models.dao.IFacturaItemDao;
import com.factura.app.models.entity.ItemFactura;
@Service
public class ItemFacturaServiceImpl implements IFacturaItemService {

	@Autowired
	private IFacturaItemDao itemDao;

	@Override
	@Transactional(readOnly = true)
	public List<ItemFactura> findAll() {
		return (List<ItemFactura>) itemDao.findAll();
	}

	@Override
	@Transactional
	public void save(ItemFactura facturaItem) {
		itemDao.save(facturaItem);
	}

	@Override
	@Transactional(readOnly = true)
	public ItemFactura findOne(Long id) {
		return itemDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		itemDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ItemFactura> findByNombreFacturaItem(String term) {
		return null;
	}

}
