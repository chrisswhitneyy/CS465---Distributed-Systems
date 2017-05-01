package appserver.comm;

import java.io.Serializable;

/**
 * Class [Message] Defines a generic Message that has a message type and content.
 * Instances of this class can be sent over a network, using object streams.
 * Message types are defined in MessageTypes
 *
 * Author Christopher D. whitney on April 28th, 2017
 */
public class Message implements MessageTypes, Serializable {

    // contains the type of message, types are defined in interface MessageTypes
    private int type;
    // contains the content that is specific to a certain message type
    private Object content;

    public Message() {}

    // getter and setter methods for message type
    public void setType(int type) {
        this.type = type;
    }

    // getter and setter methods for parameters
    public int getType() {
        return type;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Object getContent() {
        return content;
    }
}
