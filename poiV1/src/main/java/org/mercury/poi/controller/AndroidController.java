package org.mercury.poi.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.mercury.poi.service.PoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

@Controller
@RequestMapping("/android")
public class AndroidController {

	protected static Logger logger = Logger.getLogger("controller");
	private static final String XML_VIEW_NAME = "poi";
	
	@Autowired
	private PoiService poiService;
	
	@Autowired
	private Jaxb2Marshaller jaxb2Marshaller;
	
	/*@RequestMapping(value = "/addpoi", method = RequestMethod.GET)
	public String addPoi(@RequestParam String nev, @RequestParam String tipus, 
					@RequestParam String cim, @RequestParam String szelesseg, 
					@RequestParam String hosszusag, @RequestParam String minosites) {
		logger.info(tipus);
		return "home";
	}*/
	
	@RequestMapping(value = "/addpoi", method = RequestMethod.POST)
	public String addPoi(HttpServletRequest request) {
		
		MultipartHttpServletRequest multiPartRequest = new DefaultMultipartHttpServletRequest(request);
		
		String param = multiPartRequest.getParameter("param");
		
		
		logger.info(param);
		return "home";
	}
	
	/*@RequestMapping(method=RequestMethod.GET, value="/employee/{id}")
	public ModelAndView getEmployee(@PathVariable String id) {
		Employee e = employeeDS.get(Long.parseLong(id));
		return new ModelAndView(XML_VIEW_NAME, "object", e);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/employee/{id}")
	public ModelAndView updateEmployee(@RequestBody String body) {
		Source source = new StreamSource(new StringReader(body));
		Employee e = (Employee) jaxb2Mashaller.unmarshal(source);
		employeeDS.update(e);
		return new ModelAndView(XML_VIEW_NAME, "object", e);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/employee")
	public ModelAndView addEmployee(@RequestBody String body) {
		Source source = new StreamSource(new StringReader(body));
		Employee e = (Employee) jaxb2Mashaller.unmarshal(source);
		employeeDS.add(e);
		return new ModelAndView(XML_VIEW_NAME, "object", e);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/employee/{id}")
	public ModelAndView removeEmployee(@PathVariable String id) {
		employeeDS.remove(Long.parseLong(id));
		List<Employee> employees = employeeDS.getAll();
		EmployeeList list = new EmployeeList(employees);
		return new ModelAndView(XML_VIEW_NAME, "employees", list);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/employees")
	public ModelAndView getEmployees() {
		List<Employee> employees = employeeDS.getAll();
		EmployeeList list = new EmployeeList(employees);
		return new ModelAndView(XML_VIEW_NAME, "employees", list);
	}*/
	
}
