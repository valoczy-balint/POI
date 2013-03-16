package org.mercury.poi.dao;

import javassist.NotFoundException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.mercury.poi.entity.Poi;
import org.mercury.poi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
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

	
	public boolean add(Poi poi) {
		logger.info("Received request to add poi to database");
		
		try {
			sessionFactory.getCurrentSession().save(poi);
			return true;
		} catch (HibernateException e) {
			logger.error("Unable to add poi to database.", e);
			return false;
		}
	}
	
	public User getUser(String username) throws NotFoundException{
		
		logger.info("Dao - getUser()");
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

	public void addUser (User user) {
		logger.info("Received request to add user to database");
		
		try {
			sessionFactory.getCurrentSession().save(user);
		} catch (HibernateException e) {
			logger.error("Unable to add user to database.", e);
		}
	}
}
