package org.optigra.onionbowery.service.content;

import org.optigra.onionbowery.common.exception.ContentException;
import org.optigra.onionbowery.common.exception.ContentNotFoundException;
import org.optigra.onionbowery.dao.ContentRepository;
import org.optigra.onionbowery.model.Content;
import org.optigra.onionbowery.model.NodeContent;

public class DefaultContentService implements ContentService {

	private ContentRepository contentRepository;

	@Override
	public NodeContent getContentByPath(final String path) throws ContentNotFoundException {
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
