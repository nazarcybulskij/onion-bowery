package org.onion.bowery.facade.content;

import java.io.InputStream;

import org.onion.bowery.resource.ContentResource;
import org.optigra.onionbowery.common.exception.ContentException;
import org.optigra.onionbowery.common.exception.ContentNotFoundException;

public interface ContentFacade {

    InputStream getContentByPath(String contentPath) throws ContentNotFoundException;

    ContentResource storeContent(InputStream stream, String fileName, String path) throws ContentException ;
}