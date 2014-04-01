package org.optigra.onionbowery.di.context;

import java.util.HashMap;
import java.util.Map;

import org.optigra.onionbowery.di.bean.Bean;

/**
 * @date Mar 28, 2014
 * @author ivanursul
 *
 */
public abstract class AbstractAppContext implements AppContext {

    protected Map<String, Bean<?>> dependencies = new HashMap<String, Bean<?>>();

    @Override
    public <T> T getBean(final String beanId, final Class<T> clz) {
        return getBeanWrapper(beanId, clz).getInstance();
    }

    @SuppressWarnings("unchecked")
    protected <T> Bean<T> getBeanWrapper(final String beanId, final Class<T> clz) {
        return (Bean<T>) dependencies.get(beanId);
    }
    
    protected Bean<?> getBeanWrapper(final String beanId) {
        return getBeanWrapper(beanId, Object.class);
    }
    
    public <T> void put(final String beanId, final Bean<T> bean) {
        dependencies.put(beanId, bean);
    }
    
    public void init(final Map<String, Bean<?>> dependencies) throws Exception {
        this.dependencies = dependencies;
        dependencies();
    }
    
    public abstract void dependencies() throws Exception;
}
