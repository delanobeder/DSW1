package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Livro;

public interface ILivroService {

	Livro buscarPorId(Long id);
	
	List<Livro> buscarTodos();
	
	List<Livro> buscarPorTitulo(String titulo);
	
	void salvar(Livro livro);
	
	void excluir(Long id);
	
}
