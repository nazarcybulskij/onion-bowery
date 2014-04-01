package org.optigra.onionbowery.controller;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.log4j.Logger;
import org.optigra.onionbowery.servlet.request.RequestWrapper;
import org.optigra.onionbowery.servlet.response.ResponseWrapper;

/**
 * @date Mar 28, 2014
 * @author ivanursul
 *
 */
public abstract class AbstractController implements Controller {
    private static final Logger logger = Logger.getLogger(AbstractController.class);
    
    @Override
    public void handleException(final RequestWrapper req, final ResponseWrapper resp, final Exception e) throws IOException {
        logger.warn(e.getMessage());
        PrintWriter writer = resp.getWriter();
        
        // TODO: Temporary solution. To be removed.
        writer.write(e.getMessage());
    }

}
