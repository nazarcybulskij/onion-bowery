package org.optigra.onionbowery.service.content;

import org.optigra.onionbowery.common.exception.ContentException;
import org.optigra.onionbowery.common.exception.ContentNotFoundException;
import org.optigra.onionbowery.dao.ContentRepository;
import org.optigra.onionbowery.model.Content;

public class DefaultContentService implements ContentService {

	private ContentRepository contentRepository;

	@Override
	public Content getContentByPath(final String path) throws ContentNotFoundException {
	    return contentRepository.getContentByPath(path);
	}

	@Override
	public Content storeContent(final Content content) throws ContentException {
		return contentRepository.storeContent(content);
	}

	@Override
	public void deleteContent(final String contentPath) throws ContentException {
	    contentRepository.deleteContent(contentPath);
	}

	public void setContentRepository(final ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }


}
