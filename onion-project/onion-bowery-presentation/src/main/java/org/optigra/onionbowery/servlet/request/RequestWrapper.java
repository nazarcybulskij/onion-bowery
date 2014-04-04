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
    
    public double getDoubleParameter(final String paramName) {
        String parameter = getParameter(paramName);
        return Double.parseDouble(parameter);
    }
    
    @SuppressWarnings("unchecked")
    public <T> T getParameter(final String paramName, final T defaultValue) throws Exception {
        
        String paramValue = getParameter(paramName);
        
        T val;
        
        if(paramValue == null) {
            val = defaultValue;
        } else {
            val = (T) defaultValue.getClass().getConstructor(String.class).newInstance(paramValue);
        }
        
        return val;
    }
}
