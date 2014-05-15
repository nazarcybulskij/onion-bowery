package org.optigra.onionbowery.dao;

import org.optigra.onionbowery.model.Content;

public interface ContentRepository {
    
	Content storeContent(Content content);
	
	Content getContentByPath(String id, double version);

    void deleteContent(String contentPath);
	
}
