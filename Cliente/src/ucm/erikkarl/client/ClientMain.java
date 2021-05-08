package ucm.erikkarl.client;

import ucm.erikkarl.common.ServerInformation;

import java.io.IOException;
import java.net.Socket;

public class ClientMain {
    public static void main(String[] args) {
        System.out.println("Client");

        try (var socket = new Socket(ServerInformation.IP, ServerInformation.PORT))
        {
            var oyente = new OyenteCliente(socket);
            oyente.run();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
