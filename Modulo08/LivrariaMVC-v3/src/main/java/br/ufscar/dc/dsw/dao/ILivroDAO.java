package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Livro;

@SuppressWarnings("unchecked")
public interface ILivroDAO extends CrudRepository<Livro, Long>{

	Livro findById(long id);

	List<Livro> findAll();
	
	Livro save(Livro livro);

	void deleteById(Long id);
}