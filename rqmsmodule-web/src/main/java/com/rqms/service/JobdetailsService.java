package com.rqms.service;

// Generated Jun 9, 2014 5:54:31 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rqms.domain.Candidateprofile;
import com.rqms.domain.Jobdetails;

/**
 * Home object for domain model class Jobdetails.
 * 
 * @see com.rqms.service.Jobdetails
 * @author Hibernate Tools
 */
@Service
public class JobdetailsService {

	private static final Log log = LogFactory.getLog(JobdetailsService.class);

	/*
	 * private final SessionFactory sessionFactory = getSessionFactory();
	 * 
	 * protected SessionFactory getSessionFactory() { try { return (SessionFactory) new InitialContext() .lookup("SessionFactory"); } catch (Exception
	 * e) { log.error("Could not locate SessionFactory in JNDI", e); throw new IllegalStateException( "Could not locate SessionFactory in JNDI"); } }
	 */

	@Autowired
	SessionFactory sessionFactory;

	@Transactional
	public void persist(Jobdetails transientInstance) {
		log.debug("persisting Jobdetails instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	@Transactional
	public void attachDirty(Jobdetails instance) {
		log.debug("attaching dirty Jobdetails instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Transactional
	public void attachClean(Jobdetails instance) {
		log.debug("attaching clean Jobdetails instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Transactional
	public void delete(Jobdetails persistentInstance) {
		log.debug("deleting Jobdetails instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@Transactional
	public Jobdetails merge(Jobdetails detachedInstance) {
		log.debug("merging Jobdetails instance");
		try {
			Jobdetails result = (Jobdetails) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Transactional
	public Jobdetails findById(int id) {
		log.debug("getting Jobdetails instance with id: " + id);
		try {
			Jobdetails instance = (Jobdetails) sessionFactory.getCurrentSession().get("com.rqms.domain.Jobdetails", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Transactional
	public List findByExample(Jobdetails instance) {
		log.debug("finding Jobdetails instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("com.rqms.domain.Jobdetails").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@Transactional
	public List<Jobdetails> findByUserName(String userName) {
		log.debug("getting Jobdetails instance with userName: " + userName);
		try {
			List<Jobdetails> instance = (List<Jobdetails>) sessionFactory.getCurrentSession().createCriteria(Jobdetails.class)
					.add(Restrictions.ilike("jobrequesteduserid", userName)).list();
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	@Transactional
	public Set<Candidateprofile> findByJobID(int id){
		log.debug("getting Jobdetails instance with id: " + id);
		try {
			Jobdetails instance = (Jobdetails) sessionFactory
					.getCurrentSession().get("com.rqms.domain.Jobdetails", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			sessionFactory.getCurrentSession().refresh(instance);
			return instance.getCandidates();
			 
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
