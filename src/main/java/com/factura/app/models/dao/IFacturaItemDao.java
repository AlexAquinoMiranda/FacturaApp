package com.factura.app.models.dao;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.factura.app.models.entity.ItemFactura;

public interface IFacturaItemDao extends CrudRepository<ItemFactura, Long>{

	

}
