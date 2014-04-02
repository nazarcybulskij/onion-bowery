package org.optigra.onionbowery.controller.content;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.optigra.onionbowery.common.exception.ContentNotFoundException;
import org.optigra.onionbowery.controller.AbstractController;
import org.optigra.onionbowery.facade.content.ContentFacade;
import org.optigra.onionbowery.model.NodeContent;
import org.optigra.onionbowery.servlet.request.RequestWrapper;
import org.optigra.onionbowery.servlet.response.ResponseWrapper;

/**
 * @date Mar 28, 2014
 * @author ivanursul
 *
 */
public class ContentGetController extends AbstractController {

    private static final String CONTENT_REQUEST_TYPE = "content";
    
    private ContentFacade contentFacade;
    
    @Override
    public void handle(final RequestWrapper request, final ResponseWrapper response) throws ContentNotFoundException, IOException {
        
        String contentPath = request.getParameter("contentPath");
        String requestType = request.getParameter("requestType");
        
        NodeContent nodeContent = contentFacade.getContentByPath(contentPath);
        
        if(CONTENT_REQUEST_TYPE.equals(requestType)) {
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
