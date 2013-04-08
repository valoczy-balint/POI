package org.mercury.poi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.mercury.poi.utility.MultipartFileXmlAdapter;
import org.springframework.web.multipart.MultipartFile;

@Entity
@NamedQueries({
	@NamedQuery(
		name = "poi.search",
		query = "from Poi p " +
				"where p.name like :name and p.type like :type and p.address like :address"
	)
})
@Table(name = "places")
@XmlRootElement(name="employee")
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
	
	@Transient
	@XmlTransient
	private MultipartFile image;
	
	@Column(name = "imagePath")
	@XmlTransient
	private String imagePath;
	
	@Transient
	@XmlTransient
	private MultipartFile video;
	
	@Column(name = "videoPath")
	@XmlTransient
	private String videoPath;
	
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

	@XmlJavaTypeAdapter(MultipartFileXmlAdapter.class)
	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	@XmlTransient
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	@XmlJavaTypeAdapter(MultipartFileXmlAdapter.class)
	public MultipartFile getVideo() {
		return video;
	}

	public void setVideo(MultipartFile video) {
		this.video = video;
	}

	@XmlTransient
	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}
	
	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}
	
	
}
