package ucm.erikkarl.common.messages.fromserver;

import ucm.erikkarl.common.messages.Message;
import ucm.erikkarl.common.messages.MessageType;

public class ConfirmConection extends Message {
    protected ConfirmConection(String origin, String destiny) {
        super(MessageType.CONFIRM_CONECTION, origin, destiny);
    }
}
