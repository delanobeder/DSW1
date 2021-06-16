package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.ICompraDAO;
import br.ufscar.dc.dsw.domain.Compra;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.service.spec.ICompraService;

@Service
@Transactional(readOnly = false)
public class CompraService implements ICompraService {

	@Autowired
	ICompraDAO dao;
	
	public void salvar(Compra compra) {
		dao.save(compra);
	}

	@Transactional(readOnly = true)
	public Compra buscarPorId(Long id) {
		return dao.findById(id.longValue());
	}

	@Transactional(readOnly = true)
	public List<Compra> buscarTodosPorUsuario(Usuario u) {
		return dao.findAllByUsuario(u);
	}
}
