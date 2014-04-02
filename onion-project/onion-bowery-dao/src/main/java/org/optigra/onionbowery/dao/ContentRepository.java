package org.optigra.onionbowery.dao;

import org.optigra.onionbowery.common.exception.ContentException;
import org.optigra.onionbowery.common.exception.ContentNotFoundException;
import org.optigra.onionbowery.model.Content;
import org.optigra.onionbowery.model.NodeContent;

public interface ContentRepository {
    
	String storeContent(Content content) throws ContentException;
	
	NodeContent getContentByPath(String id) throws ContentNotFoundException;
	
}
