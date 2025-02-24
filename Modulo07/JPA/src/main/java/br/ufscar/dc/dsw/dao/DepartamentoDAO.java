package br.ufscar.dc.dsw.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import br.ufscar.dc.dsw.domain.Departamento;

public class DepartamentoDAO extends GenericDAO<Departamento>{

	@Override
	public Departamento find(Long id) {
		EntityManager em = this.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Departamento departamento = em.find(Departamento.class, id);
		tx.commit();
		em.close();
		return departamento;
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public List<Departamento> findAll() {
        EntityManager em = this.getEntityManager();
        EntityTransaction tx = em.getTransaction();
		tx.begin();
        Query q = em.createQuery("SELECT d FROM Departamento d");
        List<Departamento> lista = q.getResultList();
        tx.commit();
        em.close();
        return lista;
    }
	
    @Override
    public void save(Departamento departamento) {
        EntityManager em = this.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(departamento);
        tx.commit();
        em.close();
    }

    @Override
    public void update(Departamento departamento) {
        EntityManager em = this.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(departamento);
        tx.commit();
        em.close();
    }

    @Override
    public void delete(Long id) {
        EntityManager em = this.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        Departamento departamento = em.getReference(Departamento.class, id);
        tx.begin();
        em.remove(departamento);
        tx.commit();
        em.close();
    }
}
