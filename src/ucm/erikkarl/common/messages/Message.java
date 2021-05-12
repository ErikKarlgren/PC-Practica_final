package ucm.erikkarl.common.messages;

import java.io.Serializable;

public abstract class Message
        implements Serializable {

    private final String origin;
    private final String destiny;
    private final MessageType type;

    protected Message(MessageType type, String origin, String destiny) {
        this.origin = origin;
        this.destiny = destiny;
        this.type = type;
    }

    final MessageType getType() { return type; }

    final String getOrigin() { return origin; }

    final String getDestiny() { return destiny; }
}
