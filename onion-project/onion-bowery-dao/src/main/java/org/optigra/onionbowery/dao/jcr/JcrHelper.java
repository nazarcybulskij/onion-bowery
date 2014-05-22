package org.optigra.onionbowery.dao.jcr;

public class JcrHelper {

	private static final String ROOT = "/";
    private static final String JCR_PREFIX = "jcr:";
	
	public static boolean isVersionable(String path) {
		return !ROOT.equals(path) && !path.contains(JCR_PREFIX);
	}
	
}
