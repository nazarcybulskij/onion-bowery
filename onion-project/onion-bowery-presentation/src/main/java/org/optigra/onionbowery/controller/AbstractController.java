package org.optigra.onionbowery.controller;

import java.io.IOException;
import java.io.PrintWriter;

import org.optigra.onionbowery.resource.MessageResource;
import org.optigra.onionbowery.resource.MessageType;
import org.optigra.onionbowery.servlet.request.RequestWrapper;
import org.optigra.onionbowery.servlet.response.ResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @date Mar 28, 2014
 * @author ivanursul
 *
 */
public abstract class AbstractController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(AbstractController.class);
    
    protected static final String CONTENT_VERSION = "contentVersion";
    protected static final String CONTENT_PATH = "contentPath";
    protected static final String REQUEST_TYPE = "requestType";
    protected static final String CONTENT_REQUEST_TYPE = "content";
    
    private static final String EXCEPTION_MESSAGE = "Exception occurred: %s , message: %s ";

    // TODO : Workaround to avoid instantiating gson in every controller
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    @Override
    public void handleException(final RequestWrapper req, final ResponseWrapper resp, final Exception e) throws IOException {
        logger.warn(String.format(EXCEPTION_MESSAGE, e.getClass().getSimpleName(), e.getMessage()));
        
        PrintWriter writer = resp.getWriter();
        String message = String.format("Exception: %s, message: %s", e.getClass().getSimpleName(), e.getMessage());
        MessageResource messageResource = new MessageResource(MessageType.WARN, message);
        String json = gson.toJson(messageResource);
        writer.write(json);
    }

}
