package ucm.erikkarl.server;

import ucm.erikkarl.common.ServerInformation;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerMain {
    private static final Logger LOGGER = Logger.getLogger(ServerMain.class.getName());

    public static void main(String[] args) {
        LOGGER.setLevel(Level.ALL);
        try (var serverSocket = new ServerSocket(ServerInformation.PORT))
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
            e.printStackTrace();
        }
    }
}
