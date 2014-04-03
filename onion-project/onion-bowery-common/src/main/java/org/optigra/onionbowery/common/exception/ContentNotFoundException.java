package org.optigra.onionbowery.common.exception;

public class ContentNotFoundException extends Exception {
    private static final long serialVersionUID = -1584149167066239099L;

    public ContentNotFoundException() {
        super();
    }
    
    public ContentNotFoundException(final String message) {
        super(message);
    }
    
}
