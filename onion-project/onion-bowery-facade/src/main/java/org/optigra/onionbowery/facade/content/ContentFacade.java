package org.optigra.onionbowery.facade.content;

import java.io.InputStream;
import java.util.Map;

import org.optigra.onionbowery.common.exception.ContentException;
import org.optigra.onionbowery.resource.ContentResource;

public interface ContentFacade {

    ContentResource getContentByPath(String contentPath, double version) throws ContentException;

    ContentResource storeContent(InputStream stream, String fileName, String path, Map<String, String> attributes) throws ContentException ;

    void deleteContent(String contentPath) throws ContentException;
}