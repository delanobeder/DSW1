package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Cartao;

public interface ICartaoService {
	
	Cartao buscarPorId(Long id);
	
	List<Cartao> buscarTodos();
	
	List<Cartao> buscarPorCPF(String cpf);
	
	void salvar(Cartao cartao);
	
	void excluir(Long id);
	
	boolean cartaoTemTransacoes(Long id);
}