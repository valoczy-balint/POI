package org.mercury.poi.service;

import org.mercury.poi.dao.Dao;
import org.mercury.poi.entity.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class RegisterService {
	
	Dao dao;
	
	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	public void registerUser(User user) {
		dao.addUser(user);
	}
	
}
