package ucm.erikkarl.common.messages.fromclient;

import ucm.erikkarl.common.messages.Message;
import ucm.erikkarl.common.messages.MessageType;

public class RequestFile
        extends Message {

    private final String filename;

    public RequestFile(String origin, String destiny, String filename) {
        super(MessageType.REQUEST_FILE, origin, destiny);
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
