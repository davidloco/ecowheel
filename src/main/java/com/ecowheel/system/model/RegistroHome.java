package com.ecowheel.system.model;

// Generated 25/12/2016 01:08:21 AM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class Registro.
 * 
 * @see com.ecowheel.system.model.Registro
 * @author Hibernate Tools
 */
@Stateless
public class RegistroHome {

	private static final Log log = LogFactory.getLog(RegistroHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Registro transientInstance) {
		log.debug("persisting Registro instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Registro persistentInstance) {
		log.debug("removing Registro instance");
		try {
			entityManager.remove(merge(persistentInstance));
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Registro merge(Registro detachedInstance) {
		log.debug("merging Registro instance");
		try {
			Registro result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Registro findById(int id) {
		log.debug("getting Registro instance with id: " + id);
		try {
			Registro instance = entityManager.find(Registro.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Registro> findAll() {
		List<Registro> listRegistros = new ArrayList<Registro>();
		try {
			Query query = entityManager
					.createQuery("SELECT llan, ri, cli, e.intcertificado, e.numpeso, e.datefecha, e.intunidades, e.intid FROM Registro e JOIN e.cliente cli JOIN e.rin ri JOIN e.llanta llan");

			for (Object pibote : query.getResultList().toArray()) {
				Registro registro = new Registro();

				registro.setLlanta(((Llanta) ((Object[]) pibote)[0]));
				registro.setRin(((Rin) ((Object[]) pibote)[1]));
				registro.setCliente(((Cliente) ((Object[]) pibote)[2]));
				registro.setIntcertificado(((Integer) ((Object[]) pibote)[3]));
				registro.setNumpeso(((BigDecimal) ((Object[]) pibote)[4]));
				registro.setDatefecha(((Date) ((Object[]) pibote)[5]));
				registro.setIntunidades(((Integer) ((Object[]) pibote)[6]));
				registro.setIntid(((Integer) ((Object[]) pibote)[7]));

				listRegistros.add(registro);
			}
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
		return listRegistros;
	}

	@SuppressWarnings("unchecked")
	public List<Integer> actasXFecha(Date fecha) {
		try {
			Query query = entityManager.createQuery("SELECT e.intcertificado FROM Registro e WHERE e.datefecha = :fecha GROUP BY e.intcertificado");
			query.setParameter("fecha", fecha);
			return (List<Integer>) query.getResultList();
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
