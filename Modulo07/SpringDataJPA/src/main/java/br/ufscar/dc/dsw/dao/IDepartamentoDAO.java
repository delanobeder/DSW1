package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Departamento;

@SuppressWarnings("unchecked")
public interface IDepartamentoDAO extends CrudRepository<Departamento, Long>{

	Departamento findById(long id);

	List<Departamento> findAll();
	
	Departamento save(Departamento departamento);

	void deleteById(Long id);
}