package org.onion.bowery.service.content;

import java.io.InputStream;

import org.onion.bowery.model.Content;
import org.optigra.onionbowery.common.exception.ContentException;
import org.optigra.onionbowery.common.exception.ContentNotFoundException;

public interface ContentService {
    
	InputStream getContentByUuid(String id) throws ContentNotFoundException;

	InputStream getContentByPath(String path) throws ContentNotFoundException;
	
	String storeContent(Content content) throws ContentException;
	
}