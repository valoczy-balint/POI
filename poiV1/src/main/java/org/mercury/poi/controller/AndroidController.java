package org.mercury.poi.controller;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.mercury.poi.entity.Poi;
import org.mercury.poi.service.PoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
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
	
	@RequestMapping(value = "/addpoi", method = RequestMethod.POST)
	public void addPoi(HttpServletRequest request, HttpServletResponse response) {
		logger.info("Received request from an Android client to upload POI");
		
		Poi poi = new Poi();
		
		/**
		 * Obtainig request parameters -> fields of a POI Object
		 */
		//The values in the requestparameters are actually String arrays
		//For more information, refer to: http://prideafrica.blogspot.hu/2007/01/javalangclasscastexception.html
		Map<String, String[]> params = request.getParameterMap();		
		for(Entry<String, String[]> requestParam : params.entrySet()) 
			try {
				logger.info(requestParam.getKey() + " : " + requestParam.getValue()[0]);
				if(requestParam.getKey().equals("name"))
					poi.setName(requestParam.getValue()[0]);
				else if(requestParam.getKey().equals("type"))
					poi.setType(requestParam.getValue()[0]);
				else if(requestParam.getKey().equals("address"))
					poi.setAddress(requestParam.getValue()[0]);
				else if(requestParam.getKey().equals("latitude"))
					poi.setLatitude(Float.parseFloat(requestParam.getValue()[0]));
				else if(requestParam.getKey().equals("longitude"))
					poi.setLongitude(Float.parseFloat(requestParam.getValue()[0]));
				else if(requestParam.getKey().equals("rating"))
					poi.setRating(Float.parseFloat(requestParam.getValue()[0]));
			} catch (NumberFormatException e) {
				logger.error("Unable to parse uploaded poi's attribute:" + requestParam.getKey(), e);
			}
		
		if(ServletFileUpload.isMultipartContent(request)) {
			logger.info("Request type is multipart.");
				
			MultipartHttpServletRequest multipartRequest = (DefaultMultipartHttpServletRequest)request;
	
			/**
			 * Obtaining uploaded files
			 */
			Map<String, MultipartFile> files = multipartRequest.getFileMap();
			for(Entry<String, MultipartFile> fileEntry : files.entrySet()) {
				MultipartFile file = fileEntry.getValue();
				logger.info("File uploaded: " + file.getOriginalFilename());
				poi.setImage((CommonsMultipartFile)file);
			}
		} else {
			logger.info("Non-multipart http request, no image or video will be stored");
		}
		
		poiService.add(poi);
		response.setStatus(HttpStatus.OK.value());
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
