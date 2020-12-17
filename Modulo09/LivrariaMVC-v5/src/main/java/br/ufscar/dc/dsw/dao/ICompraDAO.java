package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Compra;
import br.ufscar.dc.dsw.domain.Usuario;

@SuppressWarnings("unchecked")
public interface ICompraDAO extends CrudRepository<Compra, Long>{

	Compra findById(long id);

	List<Compra> findAllByUsuario(Usuario u);
	
	@Query("select c from Compra c where c.usuario.id = ?1")
	List<Compra> findAllByUsuarioID(Long id);
	
	Compra save(Compra compra);
}