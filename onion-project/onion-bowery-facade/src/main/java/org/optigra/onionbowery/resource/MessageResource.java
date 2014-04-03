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
}
