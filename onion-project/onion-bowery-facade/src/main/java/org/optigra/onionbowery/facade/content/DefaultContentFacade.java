package org.optigra.onionbowery.facade.content;

import java.io.InputStream;
import java.util.Map;

import javax.annotation.Resource;

import org.optigra.onionbowery.common.exception.ContentException;
import org.optigra.onionbowery.common.exception.ContentNotFoundException;
import org.optigra.onionbowery.facade.converter.Converter;
import org.optigra.onionbowery.model.Content;
import org.optigra.onionbowery.model.NodeContent;
import org.optigra.onionbowery.resource.ContentResource;
import org.optigra.onionbowery.service.content.ContentService;

public class DefaultContentFacade implements ContentFacade {

    @Resource(name = "contentService")
    private ContentService contentService;
    
    @Resource(name = "contentConverter")
    private Converter<Content, ContentResource> contentConverter;
	
	@Override
	public NodeContent getContentByPath(final String contentPath) throws ContentNotFoundException {
	    return contentService.getContentByPath(contentPath);
	}

    @Override
    public ContentResource storeContent(final InputStream stream, final String fileName, final String path, final Map<String, String> attributes) throws ContentException {
        
        // Initialize content object
        Content content = new Content();
        content.setPath(path);
        content.setStream(stream);
        content.setFileName(fileName);
        content.setAttributes(attributes);
        
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
