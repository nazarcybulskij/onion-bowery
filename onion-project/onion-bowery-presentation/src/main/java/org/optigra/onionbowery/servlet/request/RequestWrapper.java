package org.optigra.onionbowery.servlet.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @date Mar 28, 2014
 * @author ivanursul
 *
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    /**
     * @date Mar 28, 2014
     * @author ivanursul
     * @param request
     */
    public RequestWrapper(final HttpServletRequest request) {
        super(request);
        
    }

}
