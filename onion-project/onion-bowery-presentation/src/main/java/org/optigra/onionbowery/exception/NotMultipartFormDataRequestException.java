package org.optigra.onionbowery.exception;

public class NotMultipartFormDataRequestException extends RuntimeException {
	private static final long serialVersionUID = 3083481660200750062L;
	public static final String DEFAULT_MESSAGE = "Current request doesn't have multipart/form-data enctype";

	public NotMultipartFormDataRequestException() {
		super();
	}

	public NotMultipartFormDataRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotMultipartFormDataRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotMultipartFormDataRequestException(String message) {
		super(message);
	}

	public NotMultipartFormDataRequestException(Throwable cause) {
		super(cause);
	}

}
