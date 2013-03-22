package org.mercury.poi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.mercury.poi.entity.Poi;
import org.mercury.poi.service.PoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PoiController {
	
	/*public PoiService getPoiService() { 
		return poiService;
	}

	public void setPoiService(PoiService poiService) {
		this.poiService = poiService;
	}*/

	protected static Logger logger = Logger.getLogger("controller");
	
	@Autowired
	private PoiService poiService;
	
	/*@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String getIndexPage() {
		logger.debug("Received request to show the home page");
		System.out.println("HOME");
		return "index";
	}*/
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String getHomePage() {
		logger.debug("Received request to show the home page");

		
		
		return "home";
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String getSearchPage(Model model) {
		logger.debug("Received request to show the search page");
		
		model.addAttribute("command", new Poi());

		return "search";
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String postSearchPage(@RequestBody Poi poi) {
		logger.debug("Received request to show the search page");
		 
		return "search result";
	}
	
	
	
	@RequestMapping(value = "/display/{id}", method = RequestMethod.GET)
	public String displayPoi(@PathVariable Integer id, Model model) {
		logger.debug("Received request to show the display page");
		
		logger.info("ID = " + id);
		
		Poi poi = poiService.get(id);
		
		model.addAttribute("poi", poi);
		
		if(poi.getImage() == null)
			logger.info("Image is null");
		else {
			String imageString = new String(Base64.encode(poi.getImage().getBytes()));
			model.addAttribute("imageString", imageString);
			logger.info("imageString is: " + imageString);
		}	
		return "display";
	}
	
	
	
	
	
	
	
	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public String getManagePage() {
		logger.debug("Received request to show the manage page");
		return "manage";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAddPage(Model model) {
		logger.debug("Received request to show the add page");
		
		Properties properties = new Properties();
		
		try {
			properties = PropertiesLoaderUtils.loadAllProperties("poi.types.properties");
		} catch (IOException e) {
			logger.error("Unable to load properties from file", e);
		}
			
		List<String> types = readProperties(properties);
		int[] rating = {1, 2, 3, 4, 5};
		
		model.addAttribute("types", types);
		model.addAttribute("rating", rating);
		model.addAttribute("poi", new Poi());
		
		return "add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String postAddPage(@ModelAttribute("poi") Poi poi, BindingResult bindingResult) {
		logger.debug("Received request to add poi");
		
		if(bindingResult.hasErrors()) 
			for(ObjectError error : bindingResult.getAllErrors())
				logger.error("An error occured during upload: " + error.getCode() +	 " - " + error.getDefaultMessage());

		poiService.add(poi);

		return "home";
	}
	
	private List<String> readProperties(Properties properties) {
		
		List<String> listOfProperties = new ArrayList<String>();
		
		try {
			int numOfProperties = Integer.parseInt(properties.getProperty("total"));
			for(int i = 1; i < numOfProperties+1; i++) 
				listOfProperties.add( properties.getProperty(Integer.toString(i)) );
		} catch (NumberFormatException e) {
			logger.error("Unable to read types from properties file", e);
		}	
		return listOfProperties;
	}
	
	/*@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody String postAddPage(@RequestParam(value="name", required=true) String name, 
											Model model) {
		logger.debug("Received request to add poi");
		
		Poi poi = new Poi();

		poi.setName(name);
		poiService.add(poi);

		return "Oh yeah";
	}*/
	
	
}
