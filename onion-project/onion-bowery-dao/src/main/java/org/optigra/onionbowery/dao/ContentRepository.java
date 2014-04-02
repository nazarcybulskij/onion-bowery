package org.optigra.onionbowery.dao;

import java.io.InputStream;

import org.optigra.onionbowery.common.exception.ContentException;
import org.optigra.onionbowery.common.exception.ContentNotFoundException;
import org.optigra.onionbowery.model.Content;

public interface ContentRepository {
    
	String storeContent(Content content) throws ContentException;
	
	InputStream getContentByUuid(String id) throws ContentNotFoundException;

	InputStream getContentByPath(String id) throws ContentNotFoundException;
	
}
