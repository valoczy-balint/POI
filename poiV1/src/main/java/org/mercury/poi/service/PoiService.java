package org.mercury.poi.service;

import java.util.List;

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

	public Poi get(Integer id) {
		return dao.getPoi(id);
	}
	
	public void add(Poi poi) {
		dao.addPoi(poi);
	}

	public List<Poi> search(Poi criteria) {
		
		List<Poi> result = dao.search(criteria);
		
		for(Poi p : result)
			if (p.getName() == null)
				p.setName("N/A");
			else if (p.getAddress() == null)
				p.setAddress("N/A");
			else if (p.getRating() == null)
				p.setRating(0.0f);
			else if (p.getType() == null)
				p.setType("N/A");
		
		return result;
	}
}
