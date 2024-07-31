package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.ufscar.dc.dsw.domain.Cartao;

@SuppressWarnings("unchecked")
public interface ICartaoDAO extends CrudRepository<Cartao, Long> {
	List<Cartao> findAll();

	Cartao findById(long id);

	Cartao save(Cartao editora);

	void deleteById(Long id);
	
	@Query("select c from Cartao c where CPF = :cpf")
	public List<Cartao> findByCPF(@Param("cpf") String cpf);
}