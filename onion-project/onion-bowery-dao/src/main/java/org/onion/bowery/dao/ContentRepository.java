package org.onion.bowery.dao;

import java.io.InputStream;

import org.onion.bowery.model.Content;
import org.optigra.onionbowery.common.exception.ContentException;
import org.optigra.onionbowery.common.exception.ContentNotFoundException;

public interface ContentRepository {
    
	String storeContent(Content content) throws ContentException;
	
	InputStream getContentByUuid(String id) throws ContentNotFoundException;

	InputStream getContentByPath(String id) throws ContentNotFoundException;
	
}
