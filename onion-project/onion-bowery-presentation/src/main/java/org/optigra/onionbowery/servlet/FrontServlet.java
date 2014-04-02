package org.optigra.onionbowery.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.optigra.onionbowery.di.context.AppContext;
import org.optigra.onionbowery.di.context.DefaultAppContext;
import org.optigra.onionbowery.servlet.request.RequestWrapper;
import org.optigra.onionbowery.servlet.request.dispatcher.RequestHandler;
import org.optigra.onionbowery.servlet.response.ResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Servlet, that acts like Front Controller
 * 
 * @date Mar 28, 2014
 * @author ivanursul
 * 
 */
public class FrontServlet extends HttpServlet {
    private static final long serialVersionUID = 2460827716415241950L;
    
    private static final Logger logger = LoggerFactory.getLogger(FrontServlet.class);

    private AppContext appContext;

    private RequestHandler requestHandler;

    @Override
    public void init() throws ServletException {
        try {
            appContext = new DefaultAppContext(getServletContext());
            requestHandler = appContext.getBean("requestHandler", RequestHandler.class);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    protected void service(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        requestHandler.handle(new RequestWrapper(req), new ResponseWrapper(resp));
    }

}
