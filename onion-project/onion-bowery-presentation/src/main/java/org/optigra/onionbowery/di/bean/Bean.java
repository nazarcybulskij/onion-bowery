package org.optigra.onionbowery.di.bean;



/**
 * @date Mar 28, 2014
 * @author ivanursul
 *
 */
public class Bean<T> {

    private T instance;
    
    private Class<T> clz;
    
    private Scope scope;

    public T getInstance() {
        return instance;
    }

    public void setInstance(final T instance) {
        this.instance = instance;
    }

    public Class<T> getClz() {
        return clz;
    }

    public void setClz(final Class<T> clz) {
        this.clz = clz;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(final Scope scope) {
        this.scope = scope;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((clz == null) ? 0 : clz.hashCode());
        result = prime * result + ((instance == null) ? 0 : instance.hashCode());
        result = prime * result + ((scope == null) ? 0 : scope.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Bean<?> other = (Bean<?>) obj;
        if (clz == null) {
            if (other.clz != null)
                return false;
        } else if (!clz.equals(other.clz))
            return false;
        if (instance == null) {
            if (other.instance != null)
                return false;
        } else if (!instance.equals(other.instance))
            return false;
        if (scope != other.scope)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Bean [instance=" + instance + ", clz=" + clz + ", scope=" + scope + "]";
    }
    
}
