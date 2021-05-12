package ucm.erikkarl.common.messages.fromclient;

import ucm.erikkarl.common.messages.Message;
import ucm.erikkarl.common.messages.MessageType;

public class RequestConection extends Message {
    protected RequestConection(String origin, String destiny) {
        super(MessageType.REQUEST_CONECTION, origin, destiny);
    }
}