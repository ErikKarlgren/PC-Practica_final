package ucm.erikkarl.common.messages.fromclient;

import ucm.erikkarl.common.messages.Message;
import ucm.erikkarl.common.messages.MessageType;

public class RequestAvailableFiles extends Message {
    protected RequestAvailableFiles(String origin, String destiny) {
        super(MessageType.REQUEST_AVAILABLE_FILES, origin, destiny);
    }
}
