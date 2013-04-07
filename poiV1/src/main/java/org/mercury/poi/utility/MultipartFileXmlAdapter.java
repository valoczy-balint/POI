package org.mercury.poi.utility;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.springframework.web.multipart.MultipartFile;

public class MultipartFileXmlAdapter extends XmlAdapter<Object, MultipartFile> {

	@Override
	public MultipartFile unmarshal(Object v) throws Exception {
		return null;
	}

	@Override
	public Object marshal(MultipartFile v) throws Exception {
		return null;
	}

}
