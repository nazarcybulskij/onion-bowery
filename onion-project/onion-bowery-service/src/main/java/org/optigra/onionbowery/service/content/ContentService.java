package org.optigra.onionbowery.service.content;

import org.optigra.onionbowery.common.exception.ContentException;
import org.optigra.onionbowery.model.Content;

public interface ContentService {
    
    Content getContentByPath(String path, double version) throws ContentException;
	
	Content storeContent(Content content) throws ContentException;

    void deleteContent(String contentPath) throws ContentException;
	
}