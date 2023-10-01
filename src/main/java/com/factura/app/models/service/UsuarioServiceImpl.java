package com.factura.app.models.service;

import java.util.List;

import com.factura.app.models.dao.IUsuarioDao;
import com.factura.app.models.entity.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	private IUsuarioDao userDao;

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {

		return (List<Usuario>) userDao.findAll();
	}

	@Override
	@Transactional
	public void save(Usuario usuario) {
		userDao.save(usuario);

	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findOne(Long id) {
		// TODO Auto-generated method stub
		return userDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		userDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findByNombre(String term) {
		return userDao.findByNombre("%" + term + "%");
	}

}
