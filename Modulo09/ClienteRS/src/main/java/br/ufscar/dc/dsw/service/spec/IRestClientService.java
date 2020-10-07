package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Cidade;
import br.ufscar.dc.dsw.domain.Estado;

public interface IRestClientService {
	
	Long create(Cidade cidade);	
	
	List<Cidade> get();
	List<Cidade> get(Estado estado);
	Cidade get(Long id);
	
	boolean update(Cidade cidade);
	
	boolean delete(Long id);
}
