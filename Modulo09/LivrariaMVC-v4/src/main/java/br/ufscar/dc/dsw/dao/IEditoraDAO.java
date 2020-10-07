package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Editora;

@SuppressWarnings("unchecked")
public interface IEditoraDAO extends CrudRepository<Editora, Long>{

	Editora findById(long id);

	List<Editora> findAll();
	
	Editora save(Editora editora);

	void deleteById(Long id);
}