package org.mercury.poi.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.mercury.poi.dao.Dao;
import org.mercury.poi.entity.Poi;
import org.mercury.poi.utility.FileType;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.transaction.annotation.Transactional;

//@Service
@Transactional
public class PoiService {

	protected static Logger logger = Logger.getLogger("service");
	
	private final String path = "C:\\dev\\springsource\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp2\\wtpwebapps\\poiV1\\resource";
	private final String fs = File.separator;
	
	Dao dao;
	
	public String getPath() {
		return path;
	}
	
	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}

	public Poi get(Integer id) {
		return dao.getPoi(id);
	}
	
	public boolean add(Poi poi) {
		
		if(poi.getType() != null && poi.getType().equalsIgnoreCase("Osszes"))
			poi.setType("");
		poi = fixPosition(poi);
		
		//TODO find a way to use relative path based on the context
		File image, video;

		//File parent = new File("C:" + fs + "dev" + fs + "Git" + fs + "poi" + fs + "poiV1" + fs + "src" + fs + "main" + fs + "webapp" + fs + "resource");
		
		if(poi.getImage() != null && poi.getImage().getOriginalFilename() != "") {
			try {
				image = new File(path + fs + "images" + fs + poi.getImage().getOriginalFilename());
				image.mkdirs();
				image.createNewFile();
				poi.getImage().transferTo(image);
				logger.debug("Image uploaded: " + image.getPath());
				poi.setImagePath("resource/images/" + poi.getImage().getOriginalFilename());
			} catch (NullPointerException e) {
				logger.info("No image set for the POI object.");
			} catch (IOException e) {
				logger.error("Unable to save image to disk", e);
			}
		}
		if(poi.getVideo() != null && poi.getVideo().getOriginalFilename() != "") {
			try {
				video = new File(path + fs + "videos" + fs + poi.getVideo().getOriginalFilename());
				video.mkdirs();
				video.createNewFile();
				poi.getVideo().transferTo(video);
				logger.debug("Video uploaded: " + video.getPath());
				poi.setVideoPath("resource/videos/" + poi.getVideo().getOriginalFilename());
			} catch (NullPointerException e) {
				logger.info("No video set for the POI object.");
			} catch (IOException e) {
				logger.error("Unable to save video to disk", e);
			}
		}

		return dao.addPoi(poi);
	}

	public boolean update(Poi poi) {
		return dao.updatePoi(poi);
	}
	
	public List<Poi> search(Poi criteria) {
		
		if(criteria.getName() == null)
			criteria.setName("");
		
		if(criteria.getType() == null)
			criteria.setType("");
		else if(criteria.getType().equalsIgnoreCase("Osszes"))
			criteria.setType("");
		
		if(criteria.getAddress() ==  null)
			criteria.setAddress("");
		
		List<Poi> result = dao.search(criteria);
		
		for(Poi p : result)
			if (p.getName() == null)
				p.setName("N/A");
			else if (p.getAddress() == null)
				p.setAddress("N/A");
			else if (p.getRating() == null)
				p.setRating(0.0f);
			else if (p.getType() == null)
				p.setType("N/A");
		
		return result;
	}

	public int[] getRatings() {
		int[] rating = {1, 2, 3, 4, 5};
		return rating;
	}
	
	public List<String> getTypeList() {
		
		Properties properties = new Properties();
		
		try {
			properties = PropertiesLoaderUtils.loadAllProperties("poi.types.properties");
		} catch (IOException e) {
			logger.error("Unable to load properties from file", e);
		}		
		
		return readProperties(properties);
		
	}
	
	/** Convert the value stored in the database to an actual file path
	 * @param file the filepath from the database
	 * @param fileType type of file to get: image or video
	 * @return absolute filepath
	 */
	public String getFileAbsolutePath (String file, FileType fileType) {
		if(file == null) 
			return null;
		
		file = file.substring(file.lastIndexOf("/") + 1);
		return path + fs + fileType.value() + fs + file;
	}
	
	private List<String> readProperties(Properties properties) {
		
		List<String> listOfProperties = new ArrayList<String>();
		
		try {
			int numOfProperties = Integer.parseInt(properties.getProperty("total"));
			for(int i = 0; i < numOfProperties; i++) 
				listOfProperties.add( properties.getProperty(Integer.toString(i)) );
		} catch (NumberFormatException e) {
			logger.error("Unable to read types from properties file", e);
		}	
		return listOfProperties;
	}
	
	/**
	 * Fixes the latitude and longitude values to be displayable on the browser map.<br/>
	 * Common example is an upload from an Android device, which sends huge ass integers
	 */
	private Poi fixPosition (Poi poi) {
		while(poi.getLatitude() > 90 || poi.getLatitude() < -90)
			poi.setLatitude(poi.getLatitude() / 10);
		while(poi.getLongitude() > 180 || poi.getLongitude() < -180)
			poi.setLongitude(poi.getLongitude() / 10);
		
		return poi;
	}
}
