package ucm.erikkarl.common.messages.fromserver;

import ucm.erikkarl.common.messages.Message;
import ucm.erikkarl.common.messages.MessageType;

public class ConfirmCloseConection extends Message {
    protected ConfirmCloseConection(String origin, String destiny) {
        super(MessageType.CONFIRM_CLOSE_CONECTION, origin, destiny);
    }
}
