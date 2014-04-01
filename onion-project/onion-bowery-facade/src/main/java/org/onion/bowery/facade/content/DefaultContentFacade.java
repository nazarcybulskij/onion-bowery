package org.onion.bowery.facade.content;

import java.io.InputStream;

import javax.annotation.Resource;

import org.onion.bowery.facade.converter.Converter;
import org.onion.bowery.model.Content;
import org.onion.bowery.resource.ContentResource;
import org.onion.bowery.service.content.ContentService;
import org.optigra.onionbowery.common.exception.ContentException;
import org.optigra.onionbowery.common.exception.ContentNotFoundException;

public class DefaultContentFacade implements ContentFacade {

    @Resource(name = "contentService")
    private ContentService contentService;
    
    @Resource(name = "contentConverter")
    private Converter<Content, ContentResource> contentConverter;
	
	@Override
	public InputStream getContentByPath(final String contentPath) throws ContentNotFoundException {
	    return contentService.getContentByPath(contentPath);
	}

    @Override
    public ContentResource storeContent(final InputStream stream, final String fileName, final String path) throws ContentException {
        
        // Initialize content object
        Content content = new Content();
        content.setPath(path);
        content.setStream(stream);
        content.setFileName(fileName);
        
        // Delegate work to service layer
        contentService.storeContent(content);
        
        // Object convertation due to some unnessesary field in Content model
        ContentResource contentResource = contentConverter.convert(content);
        
        return contentResource;
    }

    public void setContentService(final ContentService contentService) {
        this.contentService = contentService;
    }

    public void setContentConverter(final Converter<Content, ContentResource> contentConverter) {
        this.contentConverter = contentConverter;
    }

}
