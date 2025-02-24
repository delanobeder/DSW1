package br.ufscar.dc.dsw.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import br.ufscar.dc.dsw.domain.Disciplina;

public class DisciplinaDAO extends GenericDAO<Disciplina> {

	@Override
	public Disciplina find(Long id) {
		EntityManager em = this.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Disciplina disciplina = em.find(Disciplina.class, id);
		tx.commit();
		em.close();
		return disciplina;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Disciplina> findAll() {
		EntityManager em = this.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Query q = em.createQuery("SELECT d FROM Disciplina d");
		List<Disciplina> lista = q.getResultList();
		tx.commit();
		em.close();
		return lista;
	}

	@Override
	public void save(Disciplina disciplina) {
		EntityManager em = this.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(disciplina);
		tx.commit();
		em.close();
	}

	@Override
	public void update(Disciplina disciplina) {
		EntityManager em = this.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.merge(disciplina);
		tx.commit();
		em.close();
	}

	@Override
	public void delete(Long id) {
		EntityManager em = this.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		Disciplina disciplina = em.getReference(Disciplina.class, id);
		tx.begin();
		em.remove(disciplina);
		tx.commit();
		em.close();
	}
}
