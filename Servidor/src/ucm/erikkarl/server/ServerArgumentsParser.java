package ucm.erikkarl.server;

import ucm.erikkarl.common.logging.LoggerUtils;
import ucm.erikkarl.common.logging.MyFormatter;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerArgumentsParser {
    private ServerArgumentsParser() {/**/}

    private static final Logger LOGGER = LoggerUtils.getLoggerWithMyFormatter(ServerArgumentsParser.class.getName());

    static void parse(String[] args) {
        try
        {
            if (args.length >= 1)
            {
                var logLevel = Level.parse(args[0].toUpperCase());
                Logger.getLogger("").setLevel(logLevel);
                Logger.getLogger("").getHandlers()[0].setLevel(logLevel);
                LOGGER.info(() -> "Logging level set to " + logLevel);
            }
            Logger.getLogger("").getHandlers()[0].setFormatter(new MyFormatter());
        }
        catch (IllegalArgumentException e)
        {
            LOGGER.warning("Illegal value for logging level");
        }
    }
}
