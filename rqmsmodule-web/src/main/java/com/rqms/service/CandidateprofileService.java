package com.rqms.service;

// Generated Jun 9, 2014 5:54:31 PM by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.zkplus.spring.SpringUtil;

import com.rqms.domain.Candidateprofile;
import com.rqms.domain.Jobdetails;

/**
 * Home object for domain model class Candidateprofile.
 * 
 * @see com.rqms.service.Candidateprofile
 * @author Hibernate Tools
 */
@Service
public class CandidateprofileService {

	private static final Log log = LogFactory.getLog(CandidateprofileService.class);

	/*
	 * private final SessionFactory sessionFactory = getSessionFactory();
	 * 
	 * protected SessionFactory getSessionFactory() { try { return (SessionFactory) new InitialContext() .lookup("SessionFactory"); } catch (Exception
	 * e) { log.error("Could not locate SessionFactory in JNDI", e); throw new IllegalStateException( "Could not locate SessionFactory in JNDI"); } }
	 */

	@Autowired
	SessionFactory sessionFactory;

	@Transactional
	public void persist(Candidateprofile transientInstance) {
		log.debug("persisting Candidateprofile instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	@Transactional
	public void attachDirty(Candidateprofile instance) {
		log.debug("attaching dirty Candidateprofile instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	@Transactional
	public void attachClean(Candidateprofile instance) {
		log.debug("attaching clean Candidateprofile instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Transactional
	public void delete(Candidateprofile persistentInstance) {
		log.debug("deleting Candidateprofile instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@Transactional
	public Candidateprofile merge(Candidateprofile detachedInstance) {
		log.debug("merging Candidateprofile instance");
		try {
			Candidateprofile result = (Candidateprofile) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}
	@Transactional
	public Candidateprofile findById(int id) {
		log.debug("getting Candidateprofile instance with id: " + id);
		try {
			Candidateprofile instance = (Candidateprofile) sessionFactory.getCurrentSession().get("com.rqms.service.Candidateprofile", id);
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
	public List findByExample(Candidateprofile instance) {
		log.debug("finding Candidateprofile instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("com.rqms.service.Candidateprofile").add(Example.create(instance))
					.list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

}
