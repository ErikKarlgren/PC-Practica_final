package ucm.erikkarl.common.messages.fromserver;

import ucm.erikkarl.common.messages.Message;
import ucm.erikkarl.common.messages.MessageType;

import java.util.List;

public class SendUsersList extends Message {
    private final List<String> users;

    protected SendUsersList(String origin, String destiny, List<String> users) {
        super(MessageType.SEND_USERS_LIST, origin, destiny);
        this.users = users;
    }

    public List<String> getUsers() {
        return users;
    }
}
