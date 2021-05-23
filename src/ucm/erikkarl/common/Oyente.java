package ucm.erikkarl.common;

import ucm.erikkarl.common.logging.SocketReadyLogger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;

/**
 * Object that implements the {@link Runnable} interface and uses a {@link Socket}
 * to communicate with another process or computer. That same {@link Socket} is closed
 * automatically at the end of execution.
 */
public abstract class Oyente
        implements Runnable {
    private static final SocketReadyLogger LOGGER = SocketReadyLogger.create(Oyente.class.getName());

    protected final Socket socket;
    protected final String hostAddress;
    protected final String remoteAddress;

    protected Oyente(Socket socket) {
        if (socket.isClosed())
            throw new IllegalArgumentException("Socket must not be closed");
        else if (!socket.isBound())
            throw new IllegalArgumentException("Socket must be bound to an address");
        else if (!socket.isConnected())
            throw new IllegalArgumentException("Socket must be connected");

        this.socket = socket;
        this.hostAddress = socket.getRemoteSocketAddress().toString();
        this.remoteAddress = socket.getLocalSocketAddress().toString();
        LOGGER.setSocket(socket);
    }

    @Override
    public final void run() {
        try (socket;
             var out = new ObjectOutputStream(socket.getOutputStream());
             var in = new ObjectInputStream(socket.getInputStream()))
        {
            runUsingSocket(in, out);
        }
        catch (IOException e)
        {
            LOGGER.log(Level.SEVERE, "Error during execution while using socket", e);
        }
        catch (InterruptedException e)
        {
            LOGGER.log(Level.SEVERE, "Error due to interruption while using socket", e);
            Thread.currentThread().interrupt();
        }
        finally
        {
            LOGGER.info("Socket has been closed");
        }
    }

    /**
     * This is the actual code to be run that uses the {@link Socket}
     *  @param in  {@link ObjectInputStream} to read from. It's expected to be socket's
     *            internal {@link java.io.InputStream}
     * @param out {@link ObjectOutputStream} to write to. It's expected to be socket's
     *            internal {@link java.io.OutputStream}
     */
    protected abstract void runUsingSocket(ObjectInputStream in, ObjectOutputStream out)
            throws IOException, InterruptedException;
}
