package br.ufscar.dc.dsw.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.ufscar.dc.dsw.domain.Usuario;

public interface IUsuarioDAO extends CrudRepository<Usuario, Long> {
    @Query("SELECT u FROM Usuario u WHERE u.username = :username")
    public Usuario getUserByUsername(@Param("username") String username);
}