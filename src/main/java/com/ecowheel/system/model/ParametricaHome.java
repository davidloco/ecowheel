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
 * Home object for domain model class Parametrica.
 * 
 * @see com.ecowheel.system.model.Parametrica
 * @author Hibernate Tools
 */
@Stateless
public class ParametricaHome {

	private static final Log log = LogFactory.getLog(ParametricaHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Parametrica transientInstance) {
		log.debug("persisting Parametrica instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Parametrica persistentInstance) {
		log.debug("removing Parametrica instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Parametrica merge(Parametrica detachedInstance) {
		log.debug("merging Parametrica instance");
		try {
			Parametrica result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Parametrica findById(int id) {
		log.debug("getting Parametrica instance with id: " + id);
		try {
			Parametrica instance = entityManager.find(Parametrica.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public Parametrica findXNombre(String nombre) {
		try {
			Query query = entityManager.createQuery("SELECT e FROM Parametrica e WHERE e.strnombre = :nombre");
			query.setParameter("nombre", nombre);
			return (Parametrica) query.getSingleResult();
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Parametrica> findXNombres(String nombre) {
		try {
			Query query = entityManager.createQuery("SELECT e FROM Parametrica e WHERE e.strnombre = :nombre");
			query.setParameter("nombre", nombre);
			return (List<Parametrica>) query.getResultList();
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
