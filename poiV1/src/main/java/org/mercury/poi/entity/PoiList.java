package org.mercury.poi.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name="poiList")
public class PoiList {
	
	@XmlTransient
	private int count;
	private List<Poi> poiList;
	
	public PoiList() {}
	
	public PoiList(List<Poi> poiList) {
		this.poiList = poiList;
		this.count = poiList.size();
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	@XmlElement(name="poi", type=Poi.class)
	public List<Poi> getpoiList() {
		return poiList;
	}
	public void setpoiList(List<Poi> poiList) {
		this.poiList = poiList;
	}
}
