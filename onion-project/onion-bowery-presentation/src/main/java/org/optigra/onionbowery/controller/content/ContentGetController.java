package org.optigra.onionbowery.controller.content;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
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
    public void handle(final RequestWrapper request, final ResponseWrapper response) throws Exception {
        
        String contentPath = request.getParameter(CONTENT_PATH);
        String requestType = request.getParameter(REQUEST_TYPE, CONTENT_REQUEST_TYPE);
        double version = request.getParameter(CONTENT_VERSION, 0.0);
        
        ContentResource nodeContent = contentFacade.getContentByPath(contentPath, version);
        
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
