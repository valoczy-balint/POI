package org.mercury.poi.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.mercury.poi.entity.Poi;
import org.mercury.poi.entity.PoiList;
import org.mercury.poi.service.PoiService;
import org.mercury.poi.utility.FileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
	//private static final String XML_VIEW_NAME = "poi";
	
	@Autowired
	private PoiService poiService;
	
	@Autowired
	private Marshaller marshaller; 
		
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addpoi", method = RequestMethod.POST)
	public void addPoi(HttpServletRequest request, HttpServletResponse response) {
		logger.info("Received request from an Android client to upload POI");
		
		Poi poi = buildPoiFromParams(request.getParameterMap());
		
		/**
		 * Obtainig request parameters -> fields of a POI Object
		 */
		
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
	
	@RequestMapping(value = "/searchpoi", method = RequestMethod.POST)
	public void searchForPoi(HttpServletRequest request, HttpServletResponse response){
	
		
		@SuppressWarnings("unchecked")
		Poi criteria = buildPoiFromParams(request.getParameterMap());
		List<Poi> searchResult = poiService.search(criteria);
		
		if(searchResult.size() == 0) {
			response.setStatus(HttpStatus.NO_CONTENT.value());
			return;
		}
			
		/**
		 * Marshalling search result
		 */
		//JAXBContext context;
		//Marshaller marshaller;
		PoiList resultList = new PoiList(searchResult);
		
		try {
			//context = JAXBContext.newInstance(Poi.class, PoiList.class);
			//marshaller = context.createMarshaller();
			//marshaller.setProperty("jaxb.formatted.output", true);
			//marshaller.marshal(resultList, response.getOutputStream());
			
			OutputStream responseStream = response.getOutputStream();
			Result result = new StreamResult(responseStream);
			marshaller.marshal(resultList, result);
			response.setStatus(HttpStatus.OK.value());
			responseStream.close();
		} catch (XmlMappingException e) {
			logger.error("Unable to marshal search results", e);
		} catch (IOException e) {
			logger.error("Unable to create response entity", e);
		}
	}
	

	@RequestMapping(value = "/getimage/{id}", method = RequestMethod.GET)
	public void getImage (@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) {
		Poi poi = poiService.get(id);
		
		try {
			String filePath = poiService.getFileAbsolutePath(poi.getImagePath(), FileType.IMAGE);
			if(filePath == null) {
				logger.error("No image found. Image path is: " + filePath);
				response.setStatus(HttpStatus.NO_CONTENT.value());
				return;
			}
			File file = new File(filePath);
			
			byte[] image = IOUtils.toByteArray(new FileInputStream(file));
			//byte[] video = IOUtils.toByteArray(new FileInputStream(new File(poi.getVideoPath())));
			
			response.getOutputStream().write(image);
			//response.getOutputStream().write(video);
			
			response.setStatus(HttpStatus.OK.value());
			response.getOutputStream().close();
		} catch (FileNotFoundException e) {
			logger.error(e);
			response.setStatus(HttpStatus.NO_CONTENT.value());
		} catch (IOException e) {
			logger.error(e);
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		} 
	}
	
	private Poi buildPoiFromParams (Map<String, String[]> params) {
		
		Poi poi = new Poi();	
		
		//The values in the requestparameters are actually String arrays
		//For more information, refer to: http://prideafrica.blogspot.hu/2007/01/javalangclasscastexception.html
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
		
		return poi;
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
