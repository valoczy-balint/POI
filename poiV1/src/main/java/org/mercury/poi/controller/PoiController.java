package org.mercury.poi.controller;

import org.apache.log4j.Logger;
import org.mercury.poi.entity.Poi;
import org.mercury.poi.service.PoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	
	
	
	
	
	@RequestMapping(value = "/display/{id}", method = RequestMethod.GET)
	public String displayPoi(@PathVariable Integer id, Model model) {
		logger.debug("Received request to show the display page");
		
		logger.info("ID = " + id);
		
		Poi poi = poiService.get(id);
		//poi.getImageStream();
		
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
		
		model.addAttribute("poi", new Poi());
		
		return "add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String postAddPage(@ModelAttribute("poi") Poi poi, BindingResult bindingResult) {
		logger.debug("Received request to add poi");
		
		if(bindingResult.hasErrors()) 
			for(ObjectError error : bindingResult.getAllErrors())
				logger.error("An error occured during upload: " + error.getCode() +
							 " - " + error.getDefaultMessage());
		
		
		poiService.add(poi);

		return "add";
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
