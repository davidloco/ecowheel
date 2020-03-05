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
 * Home object for domain model class Llanta.
 * @see com.ecowheel.system.model.Llanta
 * @author Hibernate Tools
 */
@Stateless
public class LlantaHome {

	private static final Log log = LogFactory.getLog(LlantaHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Llanta transientInstance) {
		log.debug("persisting Llanta instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Llanta persistentInstance) {
		log.debug("removing Llanta instance");
		try {
			entityManager.remove(merge(persistentInstance));
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Llanta merge(Llanta detachedInstance) {
		log.debug("merging Llanta instance");
		try {
			Llanta result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Llanta findById(int id) {
		log.debug("getting Llanta instance with id: " + id);
		try {
			Llanta instance = entityManager.find(Llanta.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Llanta> findAll() {
		try {
			Query query = entityManager.createQuery("SELECT e FROM Llanta e ORDER BY e.strnombre");
			return (List<Llanta>) query.getResultList();
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
