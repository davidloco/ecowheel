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
 * Home object for domain model class Rin.
 * @see com.ecowheel.system.model.Rin
 * @author Hibernate Tools
 */
@Stateless
public class RinHome {

	private static final Log log = LogFactory.getLog(RinHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Rin transientInstance) {
		log.debug("persisting Rin instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Rin persistentInstance) {
		log.debug("removing Rin instance");
		try {
			entityManager.remove(merge(persistentInstance));
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Rin merge(Rin detachedInstance) {
		log.debug("merging Rin instance");
		try {
			Rin result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Rin findById(int id) {
		log.debug("getting Rin instance with id: " + id);
		try {
			Rin instance = entityManager.find(Rin.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Rin> findAll() {
		try {
			Query query = entityManager.createQuery("SELECT e FROM Rin e ORDER BY e.strnombre");
			return (List<Rin>) query.getResultList();
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
