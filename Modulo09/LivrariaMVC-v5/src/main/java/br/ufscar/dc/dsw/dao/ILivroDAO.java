package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Livro;

@SuppressWarnings("unchecked")
public interface ILivroDAO extends CrudRepository<Livro, Long>{

	Livro findById(long id);

	List<Livro> findAll();
	
	@Query("select l from Livro l where l.titulo like %?1%")
	List<Livro> findAllByTitulo(String titulo);
	
	Livro save(Livro livro);

	void deleteById(Long id);
}