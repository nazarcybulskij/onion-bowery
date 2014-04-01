package org.optigra.onionbowery.servlet.request.dispatcher;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.optigra.onionbowery.controller.Controller;
import org.optigra.onionbowery.servlet.data.DataConverter;
import org.optigra.onionbowery.servlet.request.RequestWrapper;
import org.optigra.onionbowery.servlet.response.ResponseWrapper;

/**
 * @date Mar 28, 2014
 * @author ivanursul
 *
 */
public class DefaultRequestHandler implements RequestHandler {

    private Map<String, Controller> controllers;
    
    private DataConverter dataConverter;
    
    @Override
    public void handle(final RequestWrapper req, final ResponseWrapper resp) throws IOException {
        
        String key = req.getPathInfo() + req.getMethod();
        Controller controller = controllers.get(key);

        handleRequest(req, resp, controller);
    }

    private void handleRequest(final RequestWrapper req, final ResponseWrapper resp, final Controller controller) throws IOException {
        try {
            controller.handle(req, resp);
            
            if(!resp.isWrited()) {
                PrintWriter writer = resp.getWriter();
                String json = dataConverter.write(resp.getResponseObject());
                writer.write(json);
            }
            
        } catch (Exception e) {
            controller.handleException(req, resp, e);
        }
    }

    public void setControllers(final Map<String, Controller> controllers) {
        this.controllers = controllers;
    }

    public void setDataConverter(final DataConverter dataConverter) {
        this.dataConverter = dataConverter;
    }

}
