package org.optigra.onionbowery.common.exception;

public class ContentNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -1584149167066239099L;

	public ContentNotFoundException() {
		super();
	}

	public ContentNotFoundException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ContentNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ContentNotFoundException(String message) {
		super(message);
	}

	public ContentNotFoundException(Throwable cause) {
		super(cause);
	}

}
