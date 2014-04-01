package org.onion.bowery.facade.converter;

import org.onion.bowery.model.Content;
import org.onion.bowery.resource.ContentResource;

public class ContentConverterImpl extends AbstractConverter<Content, ContentResource> {

    @Override
    public ContentResource convert(final Content source, final ContentResource target) {
        
        target.setContentId(source.getContentId());
        target.setFileName(source.getFileName());
        target.setPath(source.getPath());
        
        return target;
    }

    @Override
    public ContentResource convert(final Content source) {
        return convert(source, new ContentResource());
    }

}
