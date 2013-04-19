package org.mercury.poi.test;

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
	
	@Test
	public void addPoiTest() {
		logger.info("Testing poi insert into database");
		
		
		poiService.add(new Poi());
		
	}
}
