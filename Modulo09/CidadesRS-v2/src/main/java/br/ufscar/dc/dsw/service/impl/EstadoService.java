package br.ufscar.dc.dsw.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.IEstadoDAO;
import br.ufscar.dc.dsw.domain.Estado;
import br.ufscar.dc.dsw.service.spec.IEstadoService;

@Service
@Transactional(readOnly = false)
public class EstadoService implements IEstadoService {

	@Autowired
	IEstadoDAO dao;

	@Override
	@Transactional(readOnly = true)
	public Estado findById(long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Estado> findAll() {
		return dao.findAll();
	}

	@Override
	public void save(Estado estado) {
		dao.save(estado);
	}
	
	@Override
	public void delete(Long id) {
		dao.deleteById(id);
	}
}
