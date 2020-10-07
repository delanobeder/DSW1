package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Disciplina;

@SuppressWarnings("unchecked")
public interface IDisciplinaDAO extends CrudRepository<Disciplina, Long>{

	Disciplina findById(long id);

	List<Disciplina> findAll();
	
	Disciplina save(Disciplina disciplina);

	void deleteById(Long id);
}