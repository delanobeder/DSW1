package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.ICartaoDAO;
import br.ufscar.dc.dsw.domain.Cartao;
import br.ufscar.dc.dsw.service.spec.ICartaoService;

@Service
@Transactional(readOnly = false)
public class CartaoService implements ICartaoService {

	@Autowired
	ICartaoDAO dao;

	@Override
	@Transactional(readOnly = true)
	public Cartao buscarPorId(Long id) {
		return dao.findById(id.longValue());
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cartao> buscarTodos() {
		return dao.findAll();
	}
	
	public List<Cartao> buscarPorCPF(String cpf) {
		return dao.findByCPF(cpf);
	}
	@Override
	public void salvar(Cartao cartao) {
		dao.save(cartao);
	}
	
	@Override
	public void excluir(Long id) {
		dao.deleteById(id.longValue());
	}
	
	@Transactional(readOnly = true)
	public boolean cartaoTemTransacoes(Long id) {
		return !dao.findById(id.longValue()).getTransacoes().isEmpty();
	}
}