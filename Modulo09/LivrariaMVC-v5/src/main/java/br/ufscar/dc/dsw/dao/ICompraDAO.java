package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Compra;
import br.ufscar.dc.dsw.domain.Usuario;

@SuppressWarnings("unchecked")
public interface ICompraDAO extends CrudRepository<Compra, Long>{

	Compra findById(long id);

	List<Compra> findAllByUsuario(Usuario u);
	
	Compra save(Compra compra);
}