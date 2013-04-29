package org.mercury.poi.utility;

public enum FileType {
	IMAGE("images"),
	VIDEO("videos");
	
	private final String value;
	
	private FileType(String value) {
		this.value = value;
	}
	
	public String value() {
		return this.value;
	}
}
