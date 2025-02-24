package br.ufscar.dc.dsw.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import br.ufscar.dc.dsw.domain.Departamento;
import br.ufscar.dc.dsw.domain.Professor;

public class ProfessorDAO extends PessoaDAO {

	public List<Professor> findbyDepartamento(Departamento departamento) {
		EntityManager em = this.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		String jpql = "SELECT p FROM Professor p where p.departamento = :departamento";
		TypedQuery<Professor> q = em.createQuery(jpql, Professor.class);
		q.setParameter("departamento", departamento);
		List<Professor> lista = q.getResultList();
		tx.commit();
		em.close();
		return lista;
	}
}
