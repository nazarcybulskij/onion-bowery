package org.optigra.onionbowery.servlet.data;

import com.google.gson.Gson;

/**
 * @date Apr 1, 2014
 * @author ivanursul
 *
 */
public class DefaultDataConverter implements DataConverter {

    private Gson gson;
    
    @Override
    public <T> String write(final T object) {
        return gson.toJson(object);
    }

    @Override
    public <T> T read(final String body, final Class<T> clz) {
        return gson.fromJson(body, clz);
    }

    public void setGson(final Gson gson) {
        this.gson = gson;
    }

}
