package ucm.erikkarl.common.logging;

import java.net.Socket;
import java.util.Objects;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class MyFormatter
        extends SimpleFormatter
{
    private final String socketInfoHeader;

    public MyFormatter() {
        socketInfoHeader = "";
    }

    public MyFormatter(Socket socket) {
        Objects.requireNonNull(socket);
        if (socket.isClosed())
            throw new IllegalArgumentException("Socket must not be closed");
        else if (!socket.isBound())
            throw new IllegalArgumentException("Socket must be bound to an address");
        else if (!socket.isConnected())
            throw new IllegalArgumentException("Socket must be connected");

        var localAddress = socket.getLocalSocketAddress().toString().substring(1);
        var remoteAddress = socket.getRemoteSocketAddress().toString().substring(1);
        this.socketInfoHeader = String.format("Local address: %s - Remote address: %s%n", localAddress, remoteAddress);
    }

    @Override
    public String format(LogRecord logRecord) {
        var simpleFormat = super.format(logRecord);
        var threadInfo = String.format("Current thread: %s%n", Thread.currentThread());
        return String.format("%s%s%s%n", socketInfoHeader, threadInfo, simpleFormat);
    }
}
