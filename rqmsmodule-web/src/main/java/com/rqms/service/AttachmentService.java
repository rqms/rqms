package com.rqms.service;

// Generated Jun 9, 2014 5:54:31 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rqms.domain.Attachment;

/**
 * Home object for domain model class Attachment.
 * @see com.rqms.service.Attachment
 * @author Hibernate Tools
 */
@Service
public class AttachmentService {

	private static final Log log = LogFactory.getLog(AttachmentService.class);

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
	public void persist(Attachment transientInstance) {
		log.debug("persisting Attachment instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}
	@Transactional
	public void attachDirty(Attachment instance) {
		log.debug("attaching dirty Attachment instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	@Transactional
	public void attachClean(Attachment instance) {
		log.debug("attaching clean Attachment instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	@Transactional
	public void delete(Attachment persistentInstance) {
		log.debug("deleting Attachment instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	@Transactional
	public Attachment merge(Attachment detachedInstance) {
		log.debug("merging Attachment instance");
		try {
			Attachment result = (Attachment) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}
	@Transactional
	public Attachment findById(int id) {
		log.debug("getting Attachment instance with id: " + id);
		try {
			Attachment instance = (Attachment) sessionFactory
					.getCurrentSession().get("com.rqms.domain.Attachment", id);
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
	public List findByExample(Attachment instance) {
		log.debug("finding Attachment instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("com.rqms.domain.Attachment")
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
