package com.factura.app.models.service;

import java.util.List;

import com.factura.app.models.entity.ItemFactura;

public interface IFacturaItemService {

	public List<ItemFactura> findAll();

	public void save(ItemFactura facturaItem);

	public ItemFactura findOne(Long id);

	public void delete(Long id);

	public List<ItemFactura> findByNombreFacturaItem(String term);

}
