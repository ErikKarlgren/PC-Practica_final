package ucm.erikkarl.server;

import ucm.erikkarl.common.ServerInformation;
import ucm.erikkarl.common.logging.LoggerUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

public class ServerMain {
    private static final Logger LOGGER = LoggerUtils.getLoggerWithMyFormatter(ServerMain.class.getName());

    public static void main(String[] args) {
        ServerArgumentsParser.parse(args);

        try (var serverSocket = new ServerSocket(ServerInformation.getPort()))
        {
            LOGGER.info("Address: " + serverSocket.getInetAddress()
                                                  .getHostAddress() + ", Port: " + serverSocket.getLocalPort());

            while (!Thread.currentThread().isInterrupted())
            {
                var socket = serverSocket.accept();
                var oyente = new OyenteServidor(socket);
                new Thread(oyente).start();
            }
        }
        catch (IOException e)
        {
            LOGGER.severe(e.toString());
        }
    }
}
