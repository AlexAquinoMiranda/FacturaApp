package com.factura.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.factura.app.models.entity.Factura;
import com.factura.app.models.entity.Usuario;

public interface IFacturaDao extends CrudRepository<Factura, Long> {}
