package org.mercury.poi.utility;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class MultipartFileXmlAdapter extends XmlAdapter<Object, CommonsMultipartFile> {

	@Override
	public CommonsMultipartFile unmarshal(Object v) throws Exception {
		return null;
	}

	@Override
	public Object marshal(CommonsMultipartFile v) throws Exception {
		return null;
	}

}
