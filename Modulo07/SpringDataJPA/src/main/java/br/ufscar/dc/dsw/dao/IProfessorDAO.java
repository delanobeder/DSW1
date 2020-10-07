
package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Departamento;
import br.ufscar.dc.dsw.domain.Professor;

public interface IProfessorDAO extends CrudRepository<Professor, Long> {
	List<Professor> findByDepartamento(Departamento departamento);
}

