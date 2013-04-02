package org.mercury.poi.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
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
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PoiController {
	
	protected static Logger logger = Logger.getLogger("controller");
	
	@Autowired
	private PoiService poiService;
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String getHomePage() {
		logger.debug("Received request to show the home page");
		return "home";
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String getSearchPage(Model model) {
		logger.debug("Received request to show the search page");
		
		Properties properties = new Properties();
		
		try {
			properties = PropertiesLoaderUtils.loadAllProperties("poi.types.properties");
		} catch (IOException e) {
			logger.error("Unable to load properties from file", e);
		}
		
		List<String> types = readProperties(properties);

		model.addAttribute("types", types);
		model.addAttribute("poi", new Poi());

		return "search";
	}
	/*
	@RequestMapping(value = "/search", method = RequestMethod.POST)			//with Jacksonmapping
	public String postSearch(@ModelAttribute("poi") Poi criteria) {
		logger.debug("Received request to search for search poi");
		 
		List<Poi> result = poiService.search(criteria);

		return "home";
	}*/	
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public @ResponseBody String postSearchJson(@RequestBody String request) {
		logger.debug("Received request to search for search poi");
		 
		Poi criteria = new Poi();
		ObjectMapper mapper = new ObjectMapper();
		String result = null;
		
		try {
			criteria = mapper.readValue(request, Poi.class);
			List<Poi> searchResult = poiService.search(criteria);
			result = mapper.writeValueAsString(searchResult);
			
		} catch (JsonParseException e) {
			logger.error("Cannot parse JSON request", e);
		} catch (JsonMappingException e) {
			logger.error("Cannot map JSON request to Poi object", e);
		} catch (IOException e) {
			logger.error(e);
		}
		
		logger.debug(result);
		return result;
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
	public String postAddPage(@ModelAttribute("poi") Poi poi, BindingResult bindingResult, HttpServletRequest request) {
		logger.debug("Received request to add poi");
		
		if(bindingResult.hasErrors()) 
			for(ObjectError error : bindingResult.getAllErrors())
				logger.error("An error occured during upload: " + error.getCode() +	 " - " + error.getDefaultMessage());
		
		//TODO find a way to use relative path based on the context
		String fs = File.separator;
		File parent = new File("C:" + fs + "dev" + fs + "Git" + fs + "poi" + fs + "poiV1" + fs + "src" + fs + "main" + fs + "webapp" + fs + "resource");
		File image = new File(parent.getPath() + fs + poi.getImage().getOriginalFilename());
		
		try {
			parent.mkdirs();
			image.createNewFile();
			poi.getImage().transferTo(image);
			logger.debug("Image uploaded: " + image.getPath());
		} catch (IOException e) {
			logger.error("Unable to save image to disk", e);
		}
		
		
		//poi.setImagePath(image.getAbsolutePath());
		poi.setImagePath("resource/" + poi.getImage().getOriginalFilename());
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
