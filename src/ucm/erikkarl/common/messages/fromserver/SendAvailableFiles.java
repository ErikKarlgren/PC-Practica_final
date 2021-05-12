package ucm.erikkarl.common.messages.fromserver;

import ucm.erikkarl.common.messages.Message;
import ucm.erikkarl.common.messages.MessageType;

public class SendAvailableFiles
        extends Message {
    protected SendAvailableFiles(String origin, String destiny) {
        super(MessageType.SEND_AVAILABLE_FILES, origin, destiny);
    }
}
