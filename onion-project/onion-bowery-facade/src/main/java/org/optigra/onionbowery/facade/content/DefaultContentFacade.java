package org.optigra.onionbowery.facade.content;

import java.io.InputStream;
import java.util.Map;

import org.optigra.onionbowery.common.exception.ContentException;
import org.optigra.onionbowery.facade.converter.Converter;
import org.optigra.onionbowery.model.Content;
import org.optigra.onionbowery.resource.ContentResource;
import org.optigra.onionbowery.service.content.ContentService;

public class DefaultContentFacade implements ContentFacade {

    private ContentService contentService;
    
    private Converter<Content, ContentResource> contentConverter;
	
	@Override
	public ContentResource getContentByPath(final String contentPath, final double version) throws ContentException {
	    
	    Content content = contentService.getContentByPath(contentPath, version);
	    ContentResource contentResource = contentConverter.convert(content);
	    
	    return contentResource;
	}

    @Override
    public ContentResource storeContent(final InputStream stream, final String contentType, final String fileName, final String path, final Map<String, String> attributes) throws ContentException {
        
        // Initialize content object
        Content content = new Content();
        content.setPath(path);
        content.setContentType(contentType);
        content.setInputStream(stream);
        content.setFileName(fileName);
        content.setProperties(attributes);
        
        // Delegate work to service layer
        Content resultContent = contentService.storeContent(content);
        
        // Object convertation due to some unnessesary field in Content model
        ContentResource contentResource = contentConverter.convert(resultContent);
        
        return contentResource;
    }

    @Override
    public void deleteContent(final String contentPath) throws ContentException {
        contentService.deleteContent(contentPath);
    }

    public void setContentService(final ContentService contentService) {
        this.contentService = contentService;
    }

    public void setContentConverter(final Converter<Content, ContentResource> contentConverter) {
        this.contentConverter = contentConverter;
    }

}
