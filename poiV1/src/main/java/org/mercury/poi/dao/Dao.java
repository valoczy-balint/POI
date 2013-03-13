package org.mercury.poi.dao;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.mercury.poi.entity.Poi;
import org.mercury.poi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class Dao {
	
	@Autowired
	private SessionFactory sessionFactory;
	protected static Logger logger = Logger.getLogger("Dao");
	
	public boolean add(Poi poi) {
		logger.info("Dao - add(POI poi)");
		
		try {
			sessionFactory.getCurrentSession().save(poi);
			return true;
		} catch (HibernateException e) {
			logger.error("Unable to save poi into database.", e);
			return false;
		}
	}
	
	public User getUser(String username) {
		
		logger.info("Dao - getUser()");
		User user = null;
		
		try {
			user = (User) sessionFactory.getCurrentSession().get(User.class, username);
		} catch (HibernateException e) {
			logger.error("Unable to load user from database.", e);
		} 
		
		return user;
	}
}
