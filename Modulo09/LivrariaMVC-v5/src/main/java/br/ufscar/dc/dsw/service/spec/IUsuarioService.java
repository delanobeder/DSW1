package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Usuario;

public interface IUsuarioService {

	Usuario buscarPorId(Long id);

	List<Usuario> buscarTodos();

	void salvar(Usuario editora);

	void excluir(Long id);	
}
