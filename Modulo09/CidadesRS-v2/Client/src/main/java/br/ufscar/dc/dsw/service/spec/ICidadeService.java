package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Cidade;

public interface ICidadeService {

	Long salvar(Cidade cidade);

	List<Cidade> buscarTodos();

	Cidade buscarPorId(Long id);
	
	boolean atualizar(Cidade cidade);
	
	boolean excluir(Long id);	
}
