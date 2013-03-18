package org.mercury.poi.entity;

import java.io.IOException;
import java.io.InputStream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Entity
@Table(name = "places")
public class Poi {
	
	@Id
	@Column(name = "idplaces")
	@GeneratedValue()
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "image")
	@Lob
	private CommonsMultipartFile image;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public CommonsMultipartFile getImage() {
		return image;
	}
	
	/*public InputStream getImageStream() throws IOException {
		return image.getInputStream();
	}*/

	public void setImage(CommonsMultipartFile image) {
		this.image = image;
	}
}
