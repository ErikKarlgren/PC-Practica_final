package ucm.erikkarl.common.logging;

import java.io.IOException;
import java.util.logging.*;

/**
 * Handler that switches between working as a {@link FileHandler} and
 * as a {@link ConsoleHandler} on demand by using {@link FlexibleHandler#setHandlerMode(LoggingHandlerMode)}.
 * This handler uses the formatter {@link MyFormatter}.
 */
public class FlexibleHandler
        extends Handler {

    private static LoggingHandlerMode mode;
    private static final Handler FILE_HANDLER;

    static
    {
        Handler handler;
        try
        {
            handler = new FileHandler(System.getProperty("user.dir") + "/log.txt");
        }
        catch (IOException e)
        {
            handler = new ConsoleHandler();
        }
        FILE_HANDLER = handler;
    }

    private final Handler fileHandler;
    private final Handler consoleHandler;

    public FlexibleHandler() {
        setLevel(LoggingGlobals.LOG_LEVEL);
        var myFormatter = new MyFormatter();

        consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(myFormatter);
        consoleHandler.setLevel(LoggingGlobals.LOG_LEVEL);

        fileHandler = FILE_HANDLER;
        fileHandler.setFormatter(myFormatter);
        fileHandler.setLevel(LoggingGlobals.LOG_LEVEL);
    }

    /**
     * Sets all instances of {@link FlexibleHandler} to use the given {@link LoggingHandlerMode}.
     *
     * @param m New mode for all handlers of this class.
     */
    public static void setHandlerMode(LoggingHandlerMode m) {
        mode = m;
    }

    @Override
    public synchronized void setFormatter(Formatter formatter) {
        consoleHandler.setFormatter(formatter);
        fileHandler.setFormatter(formatter);
    }


    @Override
    public void publish(LogRecord logRecord) {
        switch (mode)
        {
            case CONSOLE -> consoleHandler.publish(logRecord);
            case FILE -> fileHandler.publish(logRecord);
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

    public enum LoggingHandlerMode {CONSOLE, FILE}
}
