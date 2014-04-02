package org.optigra.onionbowery.model;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @date Apr 2, 2014
 * @author ivanursul
 *
 */
public class NodeContent {

    private String name;
    
    private transient InputStream inputStream;
    
    private Map<String, Object> properties;
    
    private List<String> subNodes;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(final Map<String, Object> properties) {
        this.properties = properties;
    }

    public List<String> getSubNodes() {
        return subNodes;
    }

    public void setSubNodes(final List<String> subNodes) {
        this.subNodes = subNodes;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(final InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((inputStream == null) ? 0 : inputStream.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((properties == null) ? 0 : properties.hashCode());
        result = prime * result + ((subNodes == null) ? 0 : subNodes.hashCode());
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
        NodeContent other = (NodeContent) obj;
        if (inputStream == null) {
            if (other.inputStream != null)
                return false;
        } else if (!inputStream.equals(other.inputStream))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (properties == null) {
            if (other.properties != null)
                return false;
        } else if (!properties.equals(other.properties))
            return false;
        if (subNodes == null) {
            if (other.subNodes != null)
                return false;
        } else if (!subNodes.equals(other.subNodes))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "NodeContent [name=" + name + ", inputStream=" + inputStream + ", properties=" + properties + ", subNodes=" + subNodes + "]";
    }

}
