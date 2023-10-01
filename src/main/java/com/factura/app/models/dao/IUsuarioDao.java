package com.factura.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.factura.app.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

	
	@Query("select u from Usuario u where u.nombre like %?1%")
	public List<Usuario> findByNombre(String term);
}
