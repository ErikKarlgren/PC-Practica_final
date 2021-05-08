package ucm.erikkarl.server;

import ucm.erikkarl.common.ServerInformation;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerMain {
    public static void main(String[] args) {
        try (var serverSocket = new ServerSocket(ServerInformation.PORT))
        {
            System.out.println("Server >>> address: " + serverSocket.getInetAddress().getHostAddress());

            while (!Thread.currentThread().isInterrupted())
            {
                var socket = serverSocket.accept();
                var oyente = new OyenteCliente(socket);
                new Thread(oyente).start();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
