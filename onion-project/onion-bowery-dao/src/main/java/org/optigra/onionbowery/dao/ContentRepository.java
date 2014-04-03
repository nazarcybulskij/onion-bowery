package org.optigra.onionbowery.dao;

import org.optigra.onionbowery.common.exception.ContentException;
import org.optigra.onionbowery.common.exception.ContentNotFoundException;
import org.optigra.onionbowery.model.Content;

public interface ContentRepository {
    
	Content storeContent(Content content) throws ContentException;
	
	Content getContentByPath(String id) throws ContentNotFoundException;

    void deleteContent(String contentPath) throws ContentException;
	
}
