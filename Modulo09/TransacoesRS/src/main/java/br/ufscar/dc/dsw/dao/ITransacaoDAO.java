package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Transacao;

@SuppressWarnings("unchecked")
public interface ITransacaoDAO extends CrudRepository<Transacao, Long> {
	List<Transacao> findAll();
	
	Transacao findById(long id);
	
	Transacao save(Transacao transacao);

	void deleteById(Long id);
}