package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Estado;

public interface IEstadoService {

	Long salvar(Estado estado);

	List<Estado> buscarTodos();

	Estado buscarPorId(Long id);
	
	boolean atualizar(Estado estado);

	boolean excluir(Long id);
}
