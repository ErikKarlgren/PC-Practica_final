package ucm.erikkarl.common.logging;

import java.net.Socket;
import java.util.logging.Logger;

/**
 * {@link Logger} that may accept a {@link Socket} to show both host and remote addresses on logs (by default it's
 * null and has to be set manually for it to take effect, so this logger can be used without a {@link Socket}.
 * This {@link Logger} uses as {@link java.util.logging.Handler} a {@link FlexibleHandler}.
 */
public class SocketReadyLogger
        extends Logger
{
    private FlexibleHandler flexibleHandler;

    /**
     * Protected method to construct a logger for a named subsystem.
     * <p>
     * The logger will be initially configured with a null Level
     * and with useParentHandlers set to true.
     *
     * @param name A name for the logger.  This should
     *             be a dot-separated name and should normally
     *             be based on the package name or class name
     *             of the subsystem, such as java.net
     *             or javax.swing.  It may be null for anonymous Loggers.
     */
    protected SocketReadyLogger(String name) {
        super(name, null);
        setUseParentHandlers(false);
        addHandler(new FlexibleHandler());
    }

    /**
     * Creates a {@link SocketReadyLogger} with a custom name.
     *
     * @param name Name of the logger
     *
     * @return A {@link SocketReadyLogger}.
     */
    public static SocketReadyLogger create(String name) {
        return new SocketReadyLogger(name);
    }

    /**
     * Sets the {@link Socket} to be used by this logger. From here on it will log
     * socket information each time this logger is used.
     *
     * @param socket {@link Socket} to be used by the logger.
     */
    public void setSocket(Socket socket) {
        /*
        var formatter = new MyFormatter(socket);
        for (var h : getHandlers())
            h.setFormatter(formatter);
         */
        //flexibleHandler.setFormatter();
    }

    public void addHandler(FlexibleHandler handler) throws SecurityException {
        super.addHandler(handler);
        this.flexibleHandler = handler;
    }
}
