package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.ICidadeDAO;
import br.ufscar.dc.dsw.domain.Cidade;
import br.ufscar.dc.dsw.domain.Estado;
import br.ufscar.dc.dsw.service.spec.ICidadeService;

@Service
@Transactional(readOnly = false)
public class CidadeService implements ICidadeService {

	@Autowired
	ICidadeDAO dao;

	@Override
	@Transactional(readOnly = true)
	public Cidade findById(Long id) {
		return dao.findById(id.longValue());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cidade> findAll() {
		return dao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Cidade> findByEstado(Estado estado) {
		return dao.findByEstado(estado);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Cidade> findByNome(String nome) {
		return dao.findByNomeLikeIgnoreCase(nome);
	}

	@Override
	public void save(Cidade cidade) {
		dao.save(cidade);
	}
	
	@Override
	public void delete(Long id) {
		dao.deleteById(id);
	}
}
