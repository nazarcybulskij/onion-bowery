package org.optigra.onionbowery.service.content;

import java.io.InputStream;

import org.optigra.onionbowery.common.exception.ContentException;
import org.optigra.onionbowery.common.exception.ContentNotFoundException;
import org.optigra.onionbowery.model.Content;

public interface ContentService {
    
	InputStream getContentByUuid(String id) throws ContentNotFoundException;

	InputStream getContentByPath(String path) throws ContentNotFoundException;
	
	String storeContent(Content content) throws ContentException;
	
}