package ucm.erikkarl.common;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Object that implements the {@link Runnable} interface and uses a {@link Socket}
 * to communicate with another process or computer. That same {@link Socket} is closed
 * automatically at the end of execution.
 */
public abstract class SocketRunnable
        implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(SocketRunnable.class.getName());

    protected final Socket socket;

    protected SocketRunnable(Socket socket) {
        if (socket.isClosed())
            throw new IllegalArgumentException("Socket cannot be closed");
        else if (!socket.isBound())
            throw new IllegalArgumentException("Socket must be bound to an address");
        else if (!socket.isConnected())
            throw new IllegalArgumentException("Socket must be connected");

        this.socket = socket;
    }

    @Override
    public final void run() {
        try
        {
            runUsingSocket(socket);
            socket.close();
        }
        catch (IOException e)
        {
            LOGGER.severe(e.toString());
        }
    }

    /**
     * This is the actual code to be run that uses the {@link Socket}
     *
     * @param socket {@link Socket} to be used.
     */
    protected abstract void runUsingSocket(Socket socket) throws IOException;
}
