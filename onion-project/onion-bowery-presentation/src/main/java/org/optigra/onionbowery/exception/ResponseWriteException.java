package org.optigra.onionbowery.exception;

public class ResponseWriteException extends RuntimeException {
	private static final long serialVersionUID = -8452825697580652626L;
	public static final String DEFAULT_MESSAGE = "Exception occurs while writing to response";

	public ResponseWriteException() {
		super();
	}

	public ResponseWriteException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ResponseWriteException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResponseWriteException(String message) {
		super(message);
	}

	public ResponseWriteException(Throwable cause) {
		super(cause);
	}

}
