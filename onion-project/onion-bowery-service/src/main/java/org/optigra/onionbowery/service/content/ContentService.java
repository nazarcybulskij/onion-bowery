package org.optigra.onionbowery.service.content;

import org.optigra.onionbowery.model.Content;

public interface ContentService {
    
    Content getContentByPath(String path, double version);
	
	Content storeContent(Content content);

    void deleteContent(String contentPath);
	
}