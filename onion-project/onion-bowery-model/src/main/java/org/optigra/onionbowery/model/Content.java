package org.optigra.onionbowery.model;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
public class Content {
	
    private String contentId;
    
    private String fileName;
    
	private String path;
	
	private transient InputStream inputStream;
	
	private Map<String, String> properties;
	
    private List<String> subNodes;
    
    private Collection<String> versions;

	public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(final InputStream stream) {
        this.inputStream = stream;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(final String contentId) {
        this.contentId = contentId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(final Map<String, String> properties) {
        this.properties = properties;
    }

    public List<String> getSubNodes() {
        return subNodes;
    }

    public void setSubNodes(final List<String> subNodes) {
        this.subNodes = subNodes;
    }

    public Collection<String> getVersions() {
        return versions;
    }

    public void setVersions(final Collection<String> versions) {
        this.versions = versions;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((contentId == null) ? 0 : contentId.hashCode());
        result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        result = prime * result + ((properties == null) ? 0 : properties.hashCode());
        result = prime * result + ((subNodes == null) ? 0 : subNodes.hashCode());
        result = prime * result + ((versions == null) ? 0 : versions.hashCode());
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
        Content other = (Content) obj;
        if (contentId == null) {
            if (other.contentId != null)
                return false;
        } else if (!contentId.equals(other.contentId))
            return false;
        if (fileName == null) {
            if (other.fileName != null)
                return false;
        } else if (!fileName.equals(other.fileName))
            return false;
        if (path == null) {
            if (other.path != null)
                return false;
        } else if (!path.equals(other.path))
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
        if (versions == null) {
            if (other.versions != null)
                return false;
        } else if (!versions.equals(other.versions))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Content [contentId=" + contentId + ", fileName=" + fileName + ", path=" + path + ", properties=" + properties + ", subNodes=" + subNodes
                + ", versions=" + versions + "]";
    }
    
}
