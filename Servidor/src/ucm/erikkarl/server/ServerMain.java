package ucm.erikkarl.server;

import ucm.erikkarl.common.ServerInformation;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerMain {
    public static void main(String[] args) {
        try (var serverSocket = new ServerSocket(ServerInformation.PORT))
        {
            System.out.printf("Server >>> address: %s, port: %d%n", serverSocket.getInetAddress().getHostAddress(), serverSocket.getLocalPort());

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
