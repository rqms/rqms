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

import com.rqms.domain.UserProfile;

/**
 * Home object for domain model class Userprofile.
 * @see com.rqms.service.UserProfile
 * @author Hibernate Tools
 */
@Service
public class UserprofileService {

	private static final Log log = LogFactory.getLog(UserprofileService.class);

	/*private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}*/
	
	@Autowired
	SessionFactory sessionFactory;
	@Transactional
	public void persist(UserProfile transientInstance) {
		log.debug("persisting Userprofile instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}
	@Transactional
	public void attachDirty(UserProfile instance) {
		log.debug("attaching dirty Userprofile instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	@Transactional
	public void attachClean(UserProfile instance) {
		log.debug("attaching clean Userprofile instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	@Transactional
	public void delete(UserProfile persistentInstance) {
		log.debug("deleting Userprofile instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	@Transactional
	public UserProfile merge(UserProfile detachedInstance) {
		log.debug("merging Userprofile instance");
		try {
			UserProfile result = (UserProfile) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}
	@Transactional
	public UserProfile findById(long id) {
		log.debug("getting Userprofile instance with id: " + id);
		try {
			UserProfile instance = (UserProfile) sessionFactory
					.getCurrentSession()
					.get("com.rqms.domain.UserProfile", id);
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
	public List findByExample(UserProfile instance) {
		log.debug("finding Userprofile instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("com.rqms.domain.UserProfile")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
