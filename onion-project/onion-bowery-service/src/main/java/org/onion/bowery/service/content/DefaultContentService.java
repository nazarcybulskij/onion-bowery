package org.onion.bowery.service.content;

import java.io.InputStream;

import org.onion.bowery.dao.ContentRepository;
import org.onion.bowery.model.Content;
import org.optigra.onionbowery.common.exception.ContentException;
import org.optigra.onionbowery.common.exception.ContentNotFoundException;

public class DefaultContentService implements ContentService {

	private ContentRepository contentRepository;

	@Override
	public InputStream getContentByUuid(final String id) throws ContentNotFoundException {
		return contentRepository.getContentByUuid(id);
	}
	
	@Override
	public InputStream getContentByPath(final String path) throws ContentNotFoundException {
	    return contentRepository.getContentByPath(path);
	}

	@Override
	public String storeContent(final Content content) throws ContentException {
		return contentRepository.storeContent(content);
	}

    public void setContentRepository(final ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

}
