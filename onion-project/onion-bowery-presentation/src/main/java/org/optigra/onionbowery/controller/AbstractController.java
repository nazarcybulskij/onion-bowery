package org.optigra.onionbowery.controller;

import java.io.IOException;
import java.io.PrintWriter;

import org.optigra.onionbowery.exception.ResponseWriteException;
import org.optigra.onionbowery.resource.MessageResource;
import org.optigra.onionbowery.resource.MessageType;
import org.optigra.onionbowery.servlet.data.DataConverter;
import org.optigra.onionbowery.servlet.request.RequestWrapper;
import org.optigra.onionbowery.servlet.response.ResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @date Mar 28, 2014
 * @author ivanursul
 *
 */
public abstract class AbstractController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(AbstractController.class);

    private static final String EXCEPTION_MESSAGE = "Exception occurred: %s , message: %s ";
    
    protected static final String CONTENT_VERSION = "contentVersion";
    protected static final String CONTENT_PATH = "contentPath";
    protected static final String REQUEST_TYPE = "requestType";
    protected static final String CONTENT_REQUEST_TYPE = "content";
    
    private DataConverter dataConverter;
    
    @Override
    public void handleException(final RequestWrapper req, final ResponseWrapper resp, final Exception e) {
        logger.warn(String.format(EXCEPTION_MESSAGE, e.getClass().getSimpleName(), e.getMessage()), e);
        
        PrintWriter writer = getWriter(resp);
        String message = String.format("Exception: %s, message: %s", e.getClass().getSimpleName(), e.getMessage());
        
        MessageResource messageResource = new MessageResource(MessageType.WARN, message);
        String json = dataConverter.write(messageResource);
        
        writer.write(json);
    }

	private PrintWriter getWriter(final ResponseWrapper resp) {
		PrintWriter writer = null;
		try {
			writer = resp.getWriter();
		} catch (IOException e) {
			throw new ResponseWriteException(ResponseWriteException.DEFAULT_MESSAGE, e);
		}
		
		return writer;
	}

	public DataConverter getDataConverter() {
		return dataConverter;
	}

	public void setDataConverter(DataConverter dataConverter) {
		this.dataConverter = dataConverter;
	}

}
