package org.optigra.onionbowery.di.context;

import java.util.Map;

import org.optigra.onionbowery.di.bean.Bean;




/**
 * @date Mar 28, 2014
 * @author ivanursul
 *
 */
public interface AppContext {

    <T> T getBean(final String beanId, final Class<T> clz);

    /**
     * @date Mar 31, 2014
     * @author ivanursul
     * @param dependencies
     * @throws Exception
     */
    void init(Map<String, Bean<?>> dependencies) throws Exception;
    
}
