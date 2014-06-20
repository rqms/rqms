package com.rqms.service;

// Generated Jun 9, 2014 5:54:31 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rqms.domain.ApplicantfeedbackId;

/**
 * Home object for domain model class Applicantfeedbackid.
 * 
 * @see com.rqms.service.Applicantfeedbackid
 * @author Hibernate Tools
 */
@Service
public class ApplicantfeedbackidService {

	private static final Log log = LogFactory.getLog(ApplicantfeedbackidService.class);

	/*
	 * private final SessionFactory sessionFactory = getSessionFactory();
	 * 
	 * protected SessionFactory getSessionFactory() { try { return (SessionFactory) new InitialContext() .lookup("SessionFactory"); } catch (Exception
	 * e) { log.error("Could not locate SessionFactory in JNDI", e); throw new IllegalStateException( "Could not locate SessionFactory in JNDI"); } }
	 */
	@Autowired
	SessionFactory sessionFactory;

	@Transactional
	public void persist(ApplicantfeedbackId transientInstance) {
		log.debug("persisting Applicantfeedbackid instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	@Transactional
	public void attachDirty(ApplicantfeedbackId instance) {
		log.debug("attaching dirty Applicantfeedbackid instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Transactional
	public void attachClean(ApplicantfeedbackId instance) {
		log.debug("attaching clean Applicantfeedbackid instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Transactional
	public void delete(ApplicantfeedbackId persistentInstance) {
		log.debug("deleting Applicantfeedbackid instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@Transactional
	public ApplicantfeedbackId merge(ApplicantfeedbackId detachedInstance) {
		log.debug("merging Applicantfeedbackid instance");
		try {
			ApplicantfeedbackId result = (ApplicantfeedbackId) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Transactional
	public ApplicantfeedbackId findById(int id) {
		log.debug("getting Applicantfeedbackid instance with id: " + id);
		try {
			ApplicantfeedbackId instance = (ApplicantfeedbackId) sessionFactory.getCurrentSession().get("com.rqms.domain.Applicantfeedbackid", id);
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
	public List findByExample(ApplicantfeedbackId instance) {
		log.debug("finding Applicantfeedbackid instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("com.rqms.domain.Applicantfeedbackid").add(Example.create(instance))
					.list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@Transactional
	public ApplicantfeedbackId findByUniqueIDId(int jobid, int candidateid) {
		log.debug("getting Applicantfeedbackid instance with id: " + jobid);
		try {
			ApplicantfeedbackId instance = (ApplicantfeedbackId) sessionFactory.getCurrentSession().createCriteria(ApplicantfeedbackId.class)
					.add(org.hibernate.criterion.Restrictions.eq("jobid", jobid))
					.add(org.hibernate.criterion.Restrictions.eq("candidateid", candidateid)).uniqueResult();
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
}
