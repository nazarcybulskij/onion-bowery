package org.optigra.onionbowery.controller.content;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.optigra.onionbowery.common.exception.ContentNotFoundException;
import org.optigra.onionbowery.controller.AbstractController;
import org.optigra.onionbowery.facade.content.ContentFacade;
import org.optigra.onionbowery.resource.ContentResource;
import org.optigra.onionbowery.servlet.request.RequestWrapper;
import org.optigra.onionbowery.servlet.response.ResponseWrapper;

/**
 * @date Mar 28, 2014
 * @author ivanursul
 *
 */
public class ContentGetController extends AbstractController {
    
    private ContentFacade contentFacade;
    
    @Override
    public void handle(final RequestWrapper request, final ResponseWrapper response) throws ContentNotFoundException, IOException {
        
        String contentPath = getRequestParam(request, CONTENT_PATH);
        String requestType = getRequestParam(request, REQUEST_TYPE, CONTENT_REQUEST_TYPE);
        
        ContentResource nodeContent = contentFacade.getContentByPath(contentPath);
        
        if(requestType == null || CONTENT_REQUEST_TYPE.equals(requestType)) {
            InputStream in = nodeContent.getInputStream();
            OutputStream out = response.getOutputStream();
            
            IOUtils.copy(in, out);
        }
        
        response.setResponseObject(nodeContent);
    }

    public void setContentFacade(final ContentFacade contentFacade) {
        this.contentFacade = contentFacade;
    }

}
