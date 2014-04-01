package org.onion.bowery.model;

import java.io.InputStream;
public class Content {
	
    private String contentId;
    
    private String fileName;
    
	private String path;
	
	private InputStream stream;

	public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public InputStream getStream() {
        return stream;
    }

    public void setStream(final InputStream stream) {
        this.stream = stream;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((contentId == null) ? 0 : contentId.hashCode());
        result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        result = prime * result + ((stream == null) ? 0 : stream.hashCode());
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
        if (stream == null) {
            if (other.stream != null)
                return false;
        } else if (!stream.equals(other.stream))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Content [contentId=" + contentId + ", fileName=" + fileName + ", path=" + path + ", stream=" + stream + "]";
    }

}
