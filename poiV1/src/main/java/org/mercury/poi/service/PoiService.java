package org.mercury.poi.service;

import org.mercury.poi.dao.Dao;
import org.mercury.poi.entity.Poi;
import org.springframework.transaction.annotation.Transactional;

//@Service
@Transactional
public class PoiService {

	Dao dao;
	
	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	public void add(Poi poi) {
		dao.add(poi);
	}
}
