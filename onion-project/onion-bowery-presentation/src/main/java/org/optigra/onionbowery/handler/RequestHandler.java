package org.optigra.onionbowery.handler;


import org.optigra.onionbowery.servlet.request.RequestWrapper;
import org.optigra.onionbowery.servlet.response.ResponseWrapper;

/**
 * @date Mar 28, 2014
 * @author ivanursul
 *
 */
public interface RequestHandler {

    void handle(RequestWrapper req, ResponseWrapper res);
    
}
