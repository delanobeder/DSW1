package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Estado;

@SuppressWarnings("unchecked")
public interface IEstadoDAO extends CrudRepository<Estado, Long> {
	Estado findById(long id);
	List<Estado> findAll();
	Estado save(Estado estado);
	void deleteById(Long id);
}