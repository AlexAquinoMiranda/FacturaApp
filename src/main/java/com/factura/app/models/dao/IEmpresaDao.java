package com.factura.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.factura.app.models.entity.Empresa;
import com.factura.app.models.entity.Usuario;


public interface IEmpresaDao extends CrudRepository<Empresa, Long>{

	
	@Query("select u from Empresa u where u.nombreEmpresa like %?1%")
	public List<Empresa> findByNombre(String term);
}
