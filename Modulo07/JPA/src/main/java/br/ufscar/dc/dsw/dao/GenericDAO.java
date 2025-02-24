package br.ufscar.dc.dsw.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public abstract class GenericDAO<T> {

	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAPU");

	protected EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public abstract T find(Long id);

	public abstract List<T> findAll();

	public abstract void save(T t);

	public abstract void update(T t);

	public abstract void delete(Long id);
	
	public static void close() {
		emf.close();
	}
}