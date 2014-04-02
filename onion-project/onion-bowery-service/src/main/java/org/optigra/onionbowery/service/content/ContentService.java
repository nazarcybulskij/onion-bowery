package org.optigra.onionbowery.service.content;

import org.optigra.onionbowery.common.exception.ContentException;
import org.optigra.onionbowery.common.exception.ContentNotFoundException;
import org.optigra.onionbowery.model.Content;
import org.optigra.onionbowery.model.NodeContent;

public interface ContentService {
    
	NodeContent getContentByPath(String path) throws ContentNotFoundException;
	
	String storeContent(Content content) throws ContentException;
	
}