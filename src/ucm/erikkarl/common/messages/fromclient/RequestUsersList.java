package ucm.erikkarl.common.messages.fromclient;

import ucm.erikkarl.common.messages.Message;
import ucm.erikkarl.common.messages.MessageType;

public class RequestUsersList extends Message {
    protected RequestUsersList(String origin, String destiny) {
        super(MessageType.REQUEST_USERS_LIST, origin, destiny);
    }
}
