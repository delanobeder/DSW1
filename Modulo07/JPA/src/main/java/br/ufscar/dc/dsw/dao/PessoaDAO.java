package br.ufscar.dc.dsw.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import br.ufscar.dc.dsw.domain.Pessoa;

public class PessoaDAO extends GenericDAO<Pessoa>{

	@Override
	public Pessoa find(Long id) {
		EntityManager em = this.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Pessoa pessoa = em.find(Pessoa.class, id);
		tx.commit();
		em.close();
		return pessoa;
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public List<Pessoa> findAll() {
        EntityManager em = this.getEntityManager();
        EntityTransaction tx = em.getTransaction();
		tx.begin();
        Query q = em.createQuery("SELECT p FROM Pessoa p");
        List<Pessoa> lista = q.getResultList();
        tx.commit();
        em.close();
        return lista;
    }

    @Override
    public void save(Pessoa pessoa) {
        EntityManager em = this.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(pessoa);
        tx.commit();
        em.close();
    }

    @Override
    public void update(Pessoa pessoa) {
        EntityManager em = this.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(pessoa);
        tx.commit();
        em.close();
    }

    @Override
    public void delete(Long id) {
        EntityManager em = this.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        Pessoa pessoa = em.getReference(Pessoa.class, id);
        tx.begin();
        em.remove(pessoa);
        tx.commit();
        em.close();
    }
}
