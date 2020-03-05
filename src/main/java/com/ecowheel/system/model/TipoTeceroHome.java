package com.ecowheel.system.model;

// Generated 25/12/2016 01:08:21 AM by Hibernate Tools 3.4.0.CR1

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class TipoTecero.
 * @see com.ecowheel.system.model.TipoTecero
 * @author Hibernate Tools
 */
@Stateless
public class TipoTeceroHome {

	private static final Log log = LogFactory.getLog(TipoTeceroHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(TipoTecero transientInstance) {
		log.debug("persisting TipoTecero instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TipoTecero persistentInstance) {
		log.debug("removing TipoTecero instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public TipoTecero merge(TipoTecero detachedInstance) {
		log.debug("merging TipoTecero instance");
		try {
			TipoTecero result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TipoTecero findById(int id) {
		log.debug("getting TipoTecero instance with id: " + id);
		try {
			TipoTecero instance = entityManager.find(TipoTecero.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
