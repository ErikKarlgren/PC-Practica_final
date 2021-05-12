package ucm.erikkarl.common.messages.fromclient;

import ucm.erikkarl.common.messages.Message;
import ucm.erikkarl.common.messages.MessageType;

public class RequestCloseConection extends Message {
    protected RequestCloseConection(String origin, String destiny) {
        super(MessageType.REQUEST_CLOSE_CONECTION, origin, destiny);
    }
}
