package ucm.erikkarl.common.logging;

import java.io.IOException;
import java.util.logging.*;

/**
 * Handler that switches between working as a {@link FileHandler} and
 * as a {@link ConsoleHandler} on demand by using {@link FlexibleHandler#setLoggingMode(LoggingHandlerMode)}.
 * This handler uses the formatter {@link MyFormatter}.
 */
public class FlexibleHandler
        extends Handler {

    public enum LoggingHandlerMode {CONSOLE, FILE}

    /**
     * Sets all instances of {@link FlexibleHandler} to use the given {@link LoggingHandlerMode}.
     *
     * @param m New mode for all handlers of this class.
     */
    public static void setLoggingMode(LoggingHandlerMode m) {
        mode = m;
    }

    private static LoggingHandlerMode mode;
    private final Handler fileHandler;
    private final Handler consoleHandler;

    public FlexibleHandler() {
        var myFormatter = new MyFormatter();

        consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(myFormatter);

        fileHandler = tryToCreateFileHandler();
        fileHandler.setFormatter(myFormatter);
    }

    @Override
    public synchronized void setFormatter(Formatter formatter) {
        consoleHandler.setFormatter(formatter);
        fileHandler.setFormatter(formatter);
    }

    /**
     * Tries to create a {@link FileHandler}. If one cannot be created due
     * to a {@link IOException}, a {@link ConsoleHandler} is created instead.
     *
     * @return A {@link Handler} as described above.
     */
    private Handler tryToCreateFileHandler() {
        try
        {
            return new FileHandler();
        }
        catch (IOException e)
        {
            return new ConsoleHandler();
        }
    }

    @Override
    public void publish(LogRecord record) {
        switch (mode)
        {
            case CONSOLE -> consoleHandler.publish(record);
            case FILE -> fileHandler.publish(record);
        }
    }

    @Override
    public void flush() {
        switch (mode)
        {
            case CONSOLE -> consoleHandler.flush();
            case FILE -> fileHandler.flush();
        }
    }

    @Override
    public void close() throws SecurityException {
        consoleHandler.close();
        fileHandler.close();
    }
}
