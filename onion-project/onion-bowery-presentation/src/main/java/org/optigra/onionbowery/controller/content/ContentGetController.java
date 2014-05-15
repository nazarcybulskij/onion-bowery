package org.optigra.onionbowery.controller.content;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.optigra.onionbowery.controller.AbstractController;
import org.optigra.onionbowery.exception.ResponseWriteException;
import org.optigra.onionbowery.facade.content.ContentFacade;
import org.optigra.onionbowery.resource.ContentResource;
import org.optigra.onionbowery.servlet.request.RequestWrapper;
import org.optigra.onionbowery.servlet.response.ResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Controller for getting content or metadata about it. 
 * 
 * @date Mar 28, 2014
 * @author ivanursul
 *
 */
public class ContentGetController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(ContentGetController.class);

	private ContentFacade contentFacade;

	@Override
	public void handle(final RequestWrapper request, final ResponseWrapper response) {
		String contentPath = request.getParameter(CONTENT_PATH);
		String requestType = request.getParameter(REQUEST_TYPE, CONTENT_REQUEST_TYPE);
		double version = request.getParameter(CONTENT_VERSION, 0.0);
		logger.info("Getting content, contentPath: {} , version: {} , requestType: {}", contentPath, version, requestType);

		ContentResource nodeContent = contentFacade.getContentByPath(contentPath, version);

		if (isContentResponse(requestType)) {
			writeContent(response, nodeContent);
		}

		response.setResponseObject(nodeContent);

	}

	private void writeContent(final ResponseWrapper response, ContentResource nodeContent) {
		try {
			InputStream in = nodeContent.getInputStream();
			OutputStream out = response.getOutputStream();
	
			IOUtils.copy(in, out);
		} catch (IOException e) {
			logger.warn(ResponseWriteException.DEFAULT_MESSAGE, e);
			throw new ResponseWriteException(ResponseWriteException.DEFAULT_MESSAGE, e);
		}
	}

	private boolean isContentResponse(String requestType) {
		return requestType == null || CONTENT_REQUEST_TYPE.equals(requestType);
	}

	public void setContentFacade(final ContentFacade contentFacade) {
		this.contentFacade = contentFacade;
	}

}
