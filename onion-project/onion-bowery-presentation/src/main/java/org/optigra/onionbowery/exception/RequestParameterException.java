package org.optigra.onionbowery.exception;

public class RequestParameterException extends RuntimeException {
	public static final String DEFAULT_MESSAGE = "Exception occured while rading data from request";
	private static final long serialVersionUID = -1779719010860829277L;

	public RequestParameterException() {
		super();
	}

	public RequestParameterException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RequestParameterException(String message, Throwable cause) {
		super(message, cause);
	}

	public RequestParameterException(String message) {
		super(message);
	}

	public RequestParameterException(Throwable cause) {
		super(cause);
	}

}
