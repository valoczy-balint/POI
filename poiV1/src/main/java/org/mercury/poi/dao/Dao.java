package org.mercury.poi.dao;

import java.util.List;

import javassist.NotFoundException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.mercury.poi.entity.Poi;
import org.mercury.poi.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class Dao {

	protected static Logger logger = Logger.getLogger("Dao");
	
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Poi getPoi(Integer id) {
		logger.info("Received request to retrieve a poi from the database");
		
		Poi poi = null;
		try {
			poi = (Poi) sessionFactory.getCurrentSession().get(Poi.class, id);
		} catch (HibernateException e) {
			logger.error("Unable to load poi from database", e);
		}
		return poi;
	}
	
	public void addPoi(Poi poi) {
		logger.info("Received request to add poi to database.");
		
		try {
			sessionFactory.getCurrentSession().save(poi);
		} catch (HibernateException e) {
			logger.error("Unable to add poi to database.", e);
		}
	}
	
	public void updatePoi(Poi poi) {
		logger.info("Received request to update poi in database.");
		
		try {
			sessionFactory.getCurrentSession().update(poi);
		} catch (HibernateException e) {
			logger.error("Unable to add poi to database.", e);
		}
	}
	
	@SuppressWarnings("unchecked")	// the result list of the query may contain any kind of object, not only Pois
	public List<Poi> search(Poi criteria) {
		logger.debug("Received request to search for a poi in the database");
		
		Query query = sessionFactory.getCurrentSession().getNamedQuery("poi.search");
		query.setParameter("name", "%" + criteria.getName() + "%");
		query.setParameter("type", "%" + criteria.getType() + "%");
		query.setParameter("address", "%" + criteria.getAddress() + "%");
		
		return query.list();
	}
	
	public User getUser(String username) throws NotFoundException{
		logger.info("Received request to retrieve a user from the database.");
		
		User user = null;
		try {
			user = (User) sessionFactory.getCurrentSession().get(User.class, username);
			if(user == null)
				throw new NotFoundException("No such user.");
		} catch (HibernateException e) {
			logger.error("Unable to load user from database.", e);
		} 
		return user;
	}

	public void addUser(User user) {
		logger.info("Received request to add user to database.");
		
		try {
			sessionFactory.getCurrentSession().save(user);
		} catch (HibernateException e) {
			logger.error("Unable to add user to database.", e);
		}
	}
}
