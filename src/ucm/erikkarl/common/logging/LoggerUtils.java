package ucm.erikkarl.common.logging;

import java.net.Socket;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Logger;

/**
 * Utility methods for {@link Logger}s
 */
public class LoggerUtils {
    private LoggerUtils() {/**/}

    /**
     * Creates a {@link Logger} with a {@link ConsoleHandler} as {@link java.util.logging.Handler} which
     * has a {@link MyFormatter} as {@link Formatter}.
     *
     * @param name Name of {@link Logger}
     * @return A new {@link Logger}
     */
    public static Logger getLoggerWithMyFormatter(String name) {
        var consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new MyFormatter());

        var logger = Logger.getLogger(name);
        logger.setUseParentHandlers(false);
        logger.addHandler(consoleHandler);

        return logger;
    }

    /**
     * If the {@link Logger} has at least one {@link java.util.logging.Handler}, all of its
     * {@link java.util.logging.Handler}s will use a new {@link MyFormatter} instead.
     * If it didn't have any, a new {@link ConsoleHandler} is created and it uses a new
     * {@link MyFormatter}.
     *
     * @param logger {@link Logger} whose formatter is to be changed.
     * @param socket {@link Socket} used to create a new {@link MyFormatter}.
     */
    public static void switchToMyFormatterWithSocket(Logger logger, Socket socket) {
        logger.setUseParentHandlers(false);
        if (logger.getHandlers().length > 0)
        {
            for (var handler : logger.getHandlers())
                handler.setFormatter(new MyFormatter(socket));
        }
        else
        {
            var consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new MyFormatter(socket));
            logger.addHandler(consoleHandler);
        }
    }
}
