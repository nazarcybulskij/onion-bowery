package org.optigra.onionbowery.servlet.response;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @date Mar 28, 2014
 * @author ivanursul
 *
 */
public class ResponseWrapper extends HttpServletResponseWrapper {

    private Object responseObject;
    
    private boolean writed = false;
    
    public ResponseWrapper(final HttpServletResponse response) {
        super(response);
    }

    public Object getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(final Object responseObject) {
        this.responseObject = responseObject;
    }

    public boolean isWrited() {
        return writed;
    }

    public void setWrited(final boolean writed) {
        this.writed = writed;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        writed = true;
        return super.getOutputStream();
    }
    
    @Override
    public PrintWriter getWriter() throws IOException {
        writed = true;
        return super.getWriter();
    }

}
