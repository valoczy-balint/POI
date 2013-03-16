package org.mercury.poi.controller;

import org.apache.log4j.Logger;
import org.mercury.poi.service.PoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/android")
public class AndroidController {

	protected static Logger logger = Logger.getLogger("controller");
	
	@Autowired
	private PoiService poiService;
	
	@RequestMapping(value = "/addpoi", method = RequestMethod.GET)
	public void addPoi(@RequestParam String tipus) {
		logger.info(tipus);
	}
	
}
