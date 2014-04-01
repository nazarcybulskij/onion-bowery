package org.optigra.onionbowery.controller.content;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.onion.bowery.facade.content.ContentFacade;
import org.optigra.onionbowery.common.exception.ContentNotFoundException;
import org.optigra.onionbowery.controller.AbstractController;
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
    public void handle(final RequestWrapper req, final ResponseWrapper resp) throws ContentNotFoundException, IOException {
        
        String contentPath = req.getParameter("contentPath");
        
        InputStream in = contentFacade.getContentByPath(contentPath);
        OutputStream out = resp.getOutputStream();
        
        IOUtils.copy(in, out);
    }

    public void setContentFacade(final ContentFacade contentFacade) {
        this.contentFacade = contentFacade;
    }

}
