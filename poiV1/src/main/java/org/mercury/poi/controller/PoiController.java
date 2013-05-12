package org.mercury.poi.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.mercury.poi.entity.Poi;
import org.mercury.poi.service.PoiService;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.ModelAndView;

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
		
		model.addAttribute("types", poiService.getTypeList());
		model.addAttribute("poi", new Poi());

		return "search";
	}
	
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
		
		return result;
	}	
	
	/*
	 * DO NOT DELETE, THIS MAY BE USEFUL
	@RequestMapping(value = "/search", method = RequestMethod.POST)			//with Jacksonmapping
	public String postSearch(@ModelAttribute("poi") Poi criteria) {
		logger.debug("Received request to search for search poi");
		 
		List<Poi> result = poiService.search(criteria);

		return "home";
	}*/	
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editPoi(@PathVariable Integer id, Model model) {
		logger.debug("Received request to edit poi (ID: " + id + ")");
				
		Poi poi = poiService.get(id);
		
		model.addAttribute("types", poiService.getTypeList());
		model.addAttribute("rating", poiService.getRatings());
		model.addAttribute("poi", poi);
		
		return "edit";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editPoiPost(@ModelAttribute("poi") Poi poi) {
		logger.debug("Received request to update poi (ID: " + poi.getId() + ")");
				
		poiService.update(poi);
		
		return "home";
	}

	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public String getManagePage(Model model) {
		logger.debug("Received request to show the manage page");
					
		model.addAttribute("types", poiService.getTypeList());
		model.addAttribute("poi", new Poi());
		
		return "manage";
	}
	
	@RequestMapping(value = "/manage", method = RequestMethod.POST)
	public ModelAndView postEditPage(@ModelAttribute("poi") Poi criteria, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) 
			for(ObjectError error : bindingResult.getAllErrors())
				logger.error("An error occured during search: " + error.getCode() +	 " - " + error.getDefaultMessage());
		
		List<Poi> searchResult = poiService.search(criteria);
		
		return new ModelAndView("result", "poiList", searchResult);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAddPage(Model model) {
		logger.debug("Received request to show the add page");
		
		model.addAttribute("types", poiService.getTypeList());
		model.addAttribute("rating", poiService.getRatings());
		model.addAttribute("poi", new Poi());
		
		return "add";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String postAddPage(@ModelAttribute("poi") Poi poi, BindingResult bindingResult, Principal principal) {
		logger.debug("Received request to add poi");
		
		if(bindingResult.hasErrors()) 
			for(ObjectError error : bindingResult.getAllErrors())
				logger.error("An error occured during upload: " + error.getCode() +	 " - " + error.getDefaultMessage());
		
		poi.setOwner(principal.getName());
		poiService.add(poi);

		return "home";
	}
	
	/*
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody String postAddJson(@RequestBody String request) {
		logger.debug("Received request to add poi");
		logger.debug(request); 
		
		Poi poi = new Poi();
		ObjectMapper mapper = new ObjectMapper();
		String result = "Add operation failed";
		
		try {
			poi = mapper.readValue(request, Poi.class);
			if(poi.getType().equalsIgnoreCase("Osszes"))
				poi.setType("");
			poiService.add(poi);
			result = "Success";
		} catch (JsonParseException e) {
			logger.error("Cannot parse JSON request", e);
		} catch (JsonMappingException e) {
			logger.error("Cannot map JSON request to Poi object", e);
		} catch (IOException e) {
			logger.error(e);
		}
		
		return result;
	}
	*/
		
	
	
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
