package ucm.erikkarl.client;

import ucm.erikkarl.common.ServerInformation;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientArgumentsParser {
    private ClientArgumentsParser() {/* utility class */}

    private static final Logger LOGGER = Logger.getLogger(ClientArgumentsParser.class.getName());

    static void parse(String[] args) {
        try
        {
            // Parse server's address
            if (args.length >= 1)
            {
                ServerInformation.setAddress(args[0]);
                LOGGER.info(() -> "Server's address has been set to " + args[0]);
            }
            if (args.length >= 2)
            {
                var newPort = Integer.parseInt(args[1]);
                ServerInformation.setPort(newPort);
            }
            if (args.length >= 3)
            {
                var logLevel = Level.parse(args[2].toUpperCase());
                Logger.getLogger("").setLevel(logLevel);
                Logger.getLogger("").getHandlers()[0].setLevel(logLevel);
                LOGGER.info(() -> "Logging level set to " + logLevel);
            }
        }
        catch (NumberFormatException e)
        {
            LOGGER.warning("Illegal value for port argument. Using default value.");
        }
        finally
        {
            ServerInformation.makeDataFinal();
        }
    }
}
