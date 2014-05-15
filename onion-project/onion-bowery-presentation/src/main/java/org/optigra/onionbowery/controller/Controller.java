package org.optigra.onionbowery.controller;


import org.optigra.onionbowery.servlet.request.RequestWrapper;
import org.optigra.onionbowery.servlet.response.ResponseWrapper;

/**
 * @date Mar 28, 2014
 * @author ivanursul
 *
 */
public interface Controller {
    
    void handle(RequestWrapper req, ResponseWrapper resp) throws Exception;
    
    void handleException(RequestWrapper req, ResponseWrapper resp, Exception e);

}
