package org.mercury.poi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.springframework.web.multipart.commons.CommonsMultipartFile;





@Entity
@NamedQueries({
	@NamedQuery(
		name = "poi.search",
		query = "from Poi p where p.name like :name"
	)
})
@Table(name = "places")
public class Poi {
	
	@Id
	@Column(name = "idplaces")
	@GeneratedValue()
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "longitude")
	private Float longitude;
	
	@Column(name = "latitude")
	private Float latitude;
	
	@Column(name = "image")
	@Lob
	private CommonsMultipartFile image;
	
	@Column(name = "rating")
	private Float rating;
	
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
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public CommonsMultipartFile getImage() {
		return image;
	}

	public void setImage(CommonsMultipartFile image) {
		this.image = image;
	}
	
	public void clearImage () {
		this.image = null;
	}
	
	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}
	
	
}
