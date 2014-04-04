package org.optigra.onionbowery.resource;

/**
 * Message resource.
 *
 * @author Iurii Parfeniuk
 */
public class MessageResource {

    private MessageType type;

    private Long status;

    private String message;

    /**
     * Default constructor.
     *
     * @date Jan 24, 2014
     * @author Iurii Parfeniuk
     */
    public MessageResource() {
        super();
    }

    /**
     * Construct instance by type and message.
     *
     * @date Jan 24, 2014
     * @author Iurii Parfeniuk
     * @param type
     *            of message
     * @param message
     *            - text of message
     */
    public MessageResource(final MessageType type, final String message) {
        super();
        this.type = type;
        this.message = message;
    }
    
    public MessageType getType() {
        return type;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(final Long status) {
        this.status = status;
    }

    public void setType(final MessageType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        MessageResource other = (MessageResource) obj;
        if (message == null) {
            if (other.message != null)
                return false;
        } else if (!message.equals(other.message))
            return false;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        if (type != other.type)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "MessageResource [type=" + type + ", status=" + status + ", message=" + message + "]";
    }
    
}
