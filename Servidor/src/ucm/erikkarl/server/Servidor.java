package ucm.erikkarl.server;

import ucm.erikkarl.common.ServerInformation;
import ucm.erikkarl.common.logging.LoggerUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

public class Servidor {
    private static final Logger LOGGER = LoggerUtils.getLoggerWithMyFormatter(Servidor.class.getName());

    public static void main(String[] args) {
        ServerArgumentsParser.parse(args);
        var servidor = new Servidor();
        servidor.run();
    }

    private InetAddress hostAddress;
    private int localPort;

    private Servidor() {
        try
        {
            hostAddress = InetAddress.getLocalHost();
        }
        catch (UnknownHostException e)
        {
            LOGGER.severe("Host address attribute could not be set");
            System.exit(1);
        }
    }

    private void run() {
        try (var serverSocket = new ServerSocket(ServerInformation.getDefaultPort()))
        {
            localPort = serverSocket.getLocalPort();
            LOGGER.info(() -> "Address: " + hostAddress.getHostAddress() + ", Port: " + localPort);

            while (!Thread.currentThread().isInterrupted())
            {
                var socket = serverSocket.accept();
                var oyente = new OyenteACliente(socket);
                new Thread(oyente).start();
            }
        }
        catch (IOException e)
        {
            LOGGER.severe(e.toString());
        }
        finally
        {
            LOGGER.info("Server has shut down");
        }
    }
}
