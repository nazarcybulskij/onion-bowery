package org.optigra.onionbowery.controller;

import java.io.IOException;

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
    
    @Override
    public void handleException(final RequestWrapper req, final ResponseWrapper resp, final Exception e) throws IOException {
        logger.warn(String.format(EXCEPTION_MESSAGE, e.getClass().getSimpleName(), e.getMessage()));
    }

}
