package org.optigra.onionbowery.servlet;

import java.io.IOException;

import javax.servlet.ServletOutputStream;

/**
 * @date Apr 4, 2014
 * @author ivanursul
 *
 */
public class ByteArrayServletOutputStream extends ServletOutputStream {

    private int i;
    private byte[] bytes;
    
    public ByteArrayServletOutputStream(final int length) {
        bytes = new byte[length];
        i = 0;
    }
    
    @Override
    public void write(final int b) throws IOException {
        bytes[i++] = (byte) b;
    }
    
    public byte[] getBytes() {
        return bytes;
    }
}
