package org.optigra.onionbowery.dao.jcr;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JcrHelperTest {

	
	@Test
	public void testIsVersionable() throws Exception {
		// Given
		String path = "/jcr:system/jcr:versionStorage/72/1b/0f/721b0f1d-047f-4343-938d-18f0002346d3/1.1";
		
		boolean expected = false;
		// When
		boolean actual = JcrHelper.isVersionable(path);

		// Then
		assertEquals(expected, actual);
	}

	@Test
	public void testIsVersionableWithSimplePath() throws Exception {
		// Given
		String path = "/fold1/hello/content/path/path1/1.png";
		
		boolean expected = true;
		// When
		boolean actual = JcrHelper.isVersionable(path);
		
		// Then
		assertEquals(expected, actual);
	}
}
