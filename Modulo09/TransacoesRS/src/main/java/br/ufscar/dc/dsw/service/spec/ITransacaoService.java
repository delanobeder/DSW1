package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Transacao;

public interface ITransacaoService {
	
	Transacao buscarPorId(Long id);
	
	List<Transacao> buscarTodos();
	
	void salvar(Transacao transacao);
	
	void excluir(Long id);
}