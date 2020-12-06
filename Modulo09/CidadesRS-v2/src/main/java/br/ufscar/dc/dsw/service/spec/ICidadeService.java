package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Cidade;

public interface ICidadeService {
	
	Cidade findById(Long id);
	List<Cidade> findAll();
	void save(Cidade estado);
	void delete(Long id);
	
	List<Cidade> findByEstado(Long id);
	List<Cidade> findByNome(String nome);
}