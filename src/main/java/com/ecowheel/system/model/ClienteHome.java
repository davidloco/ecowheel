package com.ecowheel.system.model;

// Generated 25/12/2016 01:08:21 AM by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class Cliente.
 * 
 * @see com.ecowheel.system.model.Cliente
 * @author Hibernate Tools
 */
@Stateless
public class ClienteHome {

	private static final Log log = LogFactory.getLog(ClienteHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Cliente transientInstance) {
		log.debug("persisting Cliente instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Cliente persistentInstance) {
		log.debug("removing Cliente instance");
		try {
			entityManager.remove(merge(persistentInstance));
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Cliente merge(Cliente detachedInstance) {
		log.debug("merging Cliente instance");
		try {
			Cliente result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Cliente findById(int id) {
		log.debug("getting Cliente instance with id: " + id);
		try {
			Cliente instance = entityManager.find(Cliente.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> findAll() {
		try {
			Query query = entityManager.createQuery("SELECT e FROM Cliente e ORDER BY e.strnombre");
			return (List<Cliente>) query.getResultList();
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public Cliente findXNit(String nit) {
		try {
			Query query = entityManager.createQuery("SELECT e FROM Cliente e WHERE e.strnit = :nit");
			query.setParameter("nit", nit);
			return (Cliente) query.getSingleResult();
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
