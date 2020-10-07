package br.ufscar.dc.dsw.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.ITransacaoDAO;
import br.ufscar.dc.dsw.domain.Transacao;
import br.ufscar.dc.dsw.service.spec.ITransacaoService;

@Service
@Transactional(readOnly = false)
public class TransacaoService implements ITransacaoService {

	@Autowired
	ITransacaoDAO dao;

	@Override
	@Transactional(readOnly = true)
	public Transacao buscarPorId(Long id) {
		return dao.findById(id.longValue());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Transacao> buscarTodos() {
		return dao.findAll();
	}

	@Override
	public void salvar(Transacao transacao) {
		dao.save(transacao);
	}
	
	@Override
	public void excluir(Long id) {
		dao.deleteById(id.longValue());
	}
}