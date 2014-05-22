package org.optigra.onionbowery.facade.converter;

import org.optigra.onionbowery.model.Content;
import org.optigra.onionbowery.resource.ContentResource;

public class ContentConverterImpl extends AbstractConverter<Content, ContentResource> {

    @Override
    public ContentResource convert(final Content source, final ContentResource target) {
        
        target.setContentId(source.getContentId());
        target.setFileName(source.getFileName());
        target.setPath(source.getPath());
        target.setInputStream(source.getInputStream());
        target.setProperties(source.getProperties());
        target.setSubNodes(convertAll(source.getSubNodes()));
        
        if(source.getVersions() != null) {
        	target.setVersions(convertAll(source.getVersions()));
        }
        
        return target;
    }

    @Override
    public ContentResource convert(final Content source) {
        return convert(source, new ContentResource());
    }

}
