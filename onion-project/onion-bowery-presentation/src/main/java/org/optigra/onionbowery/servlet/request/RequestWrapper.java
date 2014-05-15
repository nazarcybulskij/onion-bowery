package org.optigra.onionbowery.servlet.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.optigra.onionbowery.exception.RequestParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @date Mar 28, 2014
 * @author ivanursul
 *
 */
public class RequestWrapper extends HttpServletRequestWrapper {
	private static final Logger logger = LoggerFactory.getLogger(RequestWrapper.class);
	private static final String EXCEPTION_MESSAGE = "Exception occured while reading parameter from request";
	
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
	public <T> T getParameter(final String paramName, final T defaultValue) {
		String paramValue = getParameter(paramName);

		T val = null;

		try {
			if (paramValue == null) {
				val = defaultValue;
			} else {
				val = (T) defaultValue.getClass().getConstructor(String.class).newInstance(paramValue);
			}
		} catch (Exception e) {
			logger.warn(EXCEPTION_MESSAGE, e);
			throw new RequestParameterException(EXCEPTION_MESSAGE, e);
		}
		
		return val;
	}
}
