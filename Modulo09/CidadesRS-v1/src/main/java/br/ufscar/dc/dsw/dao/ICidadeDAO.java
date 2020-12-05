package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.ufscar.dc.dsw.domain.Cidade;
import br.ufscar.dc.dsw.domain.Estado;

@SuppressWarnings("unchecked")
public interface ICidadeDAO extends CrudRepository<Cidade, Long> {
	
	Cidade findById(long id);
	
	List<Cidade> findAll();
	
	Cidade save(Cidade cidade);

	void deleteById(Long id);
	
	public List<Cidade> findByNomeLikeIgnoreCase(String nome);
	
	@Query("select c from Cidade c where estado = :estado")
	public List<Cidade> findByEstado(@Param("estado") Estado estado);
}