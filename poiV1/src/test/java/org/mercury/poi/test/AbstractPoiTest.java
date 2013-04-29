package org.mercury.poi.test;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.mercury.poi.entity.Poi;
import org.mercury.poi.service.PoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextConfiguration(locations={"classpath:/test-context.xml"})
public class AbstractPoiTest extends 
		AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired 
	PoiService poiService;
	
	protected static Logger logger = Logger.getLogger("AbstractPoiTest");
	
	/**
	 * Testcase adds a poi correctly
	 */
	@Test
	public void addPoiTest() {
		logger.info("Testing poi insert into database");		
		poiService.add(new Poi());
	}
	
	/**
	 * Testcase adds a poi with an invalid type, which violates length constraint
	 */
	@Test
	public void addPoiErrorTest() {
		logger.info("Testing poi insert into database");	
		
		String poiType = "test";
		for(int i = 0; i < 10; i++)
			poiType = poiType + poiType;
		
		Poi poi = new Poi();
		poi.setType(poiType);
		
		assertFalse(poiService.add(poi));
	}
	
	@Test
	public void searchPoiTest() {
		Poi criteria = new Poi();
		
		List<Poi> searchResultList = poiService.search(criteria);
		int rowCount = countRowsInTable("places");
		
		assertEquals(rowCount, searchResultList.size());
	}
	
	@Test
	public void searchPoiInstanceTest() {
		Poi criteria = new Poi();
		criteria.setName("testPoi");
		
		List<Poi> searchResultList = poiService.search(criteria);
		int rowCount = 1;
		
		assertEquals(rowCount, searchResultList.size());
	}
	
	@Test
	public void editTest() {
		Poi poi = poiService.get(1);
		assertNotNull(poi);
		
		poi.setName("Test");
		assertEquals(poi.getName(), "Test");
		
		assertTrue(poiService.update(poi));
		
		Poi poi2 = poiService.get(1);
		assertEquals(poi2.getName(), "Test");
	}
}
