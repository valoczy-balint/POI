package org.mercury.poi.controller;

import org.apache.log4j.Logger;
import org.mercury.poi.entity.User;
import org.mercury.poi.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SecurityController {

	protected static Logger logger = Logger.getLogger("Security Controller");
	
	@Autowired
	private RegisterService registerService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)			//TODO /security/login
	public String getLoginPage(@RequestParam(value="error", required=false) boolean error, 
							   ModelMap model) {
		
		logger.debug("Received request to show login page");

		if (error) 
			model.put("error", "You have entered an invalid username or password!");
		 else 
			model.put("error", "");
		
		return "loginpage";
	}
	
	@RequestMapping(value = "/denied", method = RequestMethod.GET)
 	public String getDeniedPage() {
		
		logger.debug("Received request to show denied page");
		return "deniedpage";
		
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
 	public @ResponseBody String registerUser(@RequestParam(value="username", required=true) String username,
 											 @RequestParam(value="password", required=true) String password,
 											 Model model) {
		
		logger.debug("Received request register user");
		User user = new User();
		String encodedPassword = "pass";
		PasswordEncoder encoder = new ShaPasswordEncoder();
		
		try {
			user.setUsername(username);
			encodedPassword = encoder.encodePassword(password, null);
			user.setPassword(encodedPassword);
			user.setAccess(0);
			
			registerService.registerUser(user);
			
		} catch (Exception e) {
			logger.error("Unable to register user", e);
		}
		
		return encodedPassword;
	}
}
