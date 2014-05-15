package org.optigra.onionbowery.servlet.data;

/**
 * @date Apr 1, 2014
 * @author ivanursul
 *
 */
public interface DataConverter {

    <T> String write(T object);
    <T> T read(String body, Class<T> clz);
}
