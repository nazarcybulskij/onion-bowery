package org.optigra.onionbowery.controller.content;

import org.optigra.onionbowery.controller.AbstractController;
import org.optigra.onionbowery.facade.content.ContentFacade;
import org.optigra.onionbowery.servlet.request.RequestWrapper;
import org.optigra.onionbowery.servlet.response.ResponseWrapper;

/**
 * @date Apr 3, 2014
 * @author ivanursul
 *
 */
public class ContentDeleteController extends AbstractController {

    private static final String CONTENT_PATH = "contentPath";
    private ContentFacade contentFacade;
    
    @Override
    public void handle(final RequestWrapper req, final ResponseWrapper resp) throws Exception {
        String contentPath = req.getParameter(CONTENT_PATH);
        contentFacade.deleteContent(contentPath);
    }

    public void setContentFacade(final ContentFacade contentFacade) {
        this.contentFacade = contentFacade;
    }

}
