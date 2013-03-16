package org.mercury.poi.controller;

import org.apache.log4j.Logger;
import org.mercury.poi.entity.Poi;
import org.mercury.poi.entity.User;
import org.mercury.poi.service.PoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PoiController {
	
	protected static Logger logger = Logger.getLogger("controller");
	
	@Autowired
	private PoiService poiService;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String getIndexPage() {
		logger.debug("Received request to show the home page");
		System.out.println("HOME");
		return "index";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String getHomePage() {
		logger.debug("Received request to show the home page");
		System.out.println("HOME");
		return "home";
	}
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public String getDisplayPage() {
		logger.debug("Received request to show the display page");
		return "display";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAddPage() {
		logger.debug("Received request to show the add page");
		return "add";
	}
	
	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public String getManagePage() {
		logger.debug("Received request to show the manage page");
		return "manage";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody String postAddPage(@RequestParam(value="name", required=true) String name, 
											Model model) {
		logger.debug("Received request to add poi");
		
		Poi poi = new Poi();

		poi.setName(name);
		poiService.add(poi);

		return "Oh yeah";
	}
	
	
}
