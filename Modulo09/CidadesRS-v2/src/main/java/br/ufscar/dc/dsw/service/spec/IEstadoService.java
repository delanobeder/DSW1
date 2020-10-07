package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Estado;

public interface IEstadoService {
	Estado findById(long id);

	List<Estado> findAll();

	void save(Estado estado);

	void delete(Long id);
}