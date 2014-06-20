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

import com.rqms.domain.ValidRoleGroup;

/**
 * Home object for domain model class Validrole.
 * 
 * @see com.rqms.service.Validrole
 * @author Hibernate Tools
 */
@Service
public class ValidRoleGroupService {

	private static final Log log = LogFactory.getLog(ValidRoleGroupService.class);

	@Autowired
	SessionFactory sessionFactory;

	@Transactional
	public void persist(ValidRoleGroup transientInstance) {
		log.debug("persisting ValidRoleGroup instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	@Transactional
	public void attachDirty(ValidRoleGroup instance) {
		log.debug("attaching dirty ValidRoleGroup instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Transactional
	public void attachClean(ValidRoleGroup instance) {
		log.debug("attaching clean ValidRoleGroup instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Transactional
	public void delete(ValidRoleGroup persistentInstance) {
		log.debug("deleting ValidRoleGroup instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@Transactional
	public ValidRoleGroup merge(ValidRoleGroup detachedInstance) {
		log.debug("merging ValidRoleGroup instance");
		try {
			ValidRoleGroup result = (ValidRoleGroup) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Transactional
	public ValidRoleGroup findById(int id) {
		log.debug("getting ValidRoleGroup instance with id: " + id);
		try {
			ValidRoleGroup instance = (ValidRoleGroup) sessionFactory.getCurrentSession().get("com.rqms.domain.ValidRoleGroup", id);
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
	public List findByExample(ValidRoleGroup instance) {
		log.debug("finding ValidRoleGroup instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("com.rqms.domain.ValidRoleGroup").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
