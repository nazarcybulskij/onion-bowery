package org.optigra.onionbowery.controller.content;

import org.optigra.onionbowery.controller.AbstractController;
import org.optigra.onionbowery.facade.content.ContentFacade;
import org.optigra.onionbowery.resource.MessageResource;
import org.optigra.onionbowery.resource.MessageType;
import org.optigra.onionbowery.servlet.request.RequestWrapper;
import org.optigra.onionbowery.servlet.response.ResponseWrapper;

/**
 * Controller for deleting content
 * 
 * @date Apr 3, 2014
 * @author ivanursul
 *
 */
public class ContentDeleteController extends AbstractController {

    private ContentFacade contentFacade;
    
    @Override
    public void handle(final RequestWrapper request, final ResponseWrapper response) {
        String contentPath = request.getParameter(CONTENT_PATH);
        contentFacade.deleteContent(contentPath);
        
        MessageResource messageResource = new MessageResource(MessageType.INFO, "Message deleted successfuly");
        response.setResponseObject(messageResource);
    }

    public void setContentFacade(final ContentFacade contentFacade) {
        this.contentFacade = contentFacade;
    }

}
