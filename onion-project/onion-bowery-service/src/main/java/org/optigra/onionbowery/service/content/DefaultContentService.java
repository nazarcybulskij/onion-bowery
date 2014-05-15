package org.optigra.onionbowery.service.content;

import org.optigra.onionbowery.dao.ContentRepository;
import org.optigra.onionbowery.model.Content;

public class DefaultContentService implements ContentService {

	private ContentRepository contentRepository;

	@Override
	public Content getContentByPath(final String path, final double version) {
	    return contentRepository.getContentByPath(path, version);
	}

	@Override
	public Content storeContent(final Content content) {
		return contentRepository.storeContent(content);
	}

	@Override
	public void deleteContent(final String contentPath) {
	    contentRepository.deleteContent(contentPath);
	}

	public void setContentRepository(final ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }


}
