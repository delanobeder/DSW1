package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Cidade;
import br.ufscar.dc.dsw.domain.Estado;

public interface ICidadeService {
	
	Cidade findById(Long id);
	List<Cidade> findAll();
	void save(Cidade estado);
	void delete(Long id);
	
	List<Cidade> findByEstado(Estado estado);
	List<Cidade> findByNome(String nome);
}