package org.optigra.onionbowery.facade.content;

import java.io.InputStream;
import java.util.Map;

import org.optigra.onionbowery.common.exception.ContentException;
import org.optigra.onionbowery.common.exception.ContentNotFoundException;
import org.optigra.onionbowery.model.NodeContent;
import org.optigra.onionbowery.resource.ContentResource;

public interface ContentFacade {

    NodeContent getContentByPath(String contentPath) throws ContentNotFoundException;

    ContentResource storeContent(InputStream stream, String fileName, String path, Map<String, String> attributes) throws ContentException ;
}