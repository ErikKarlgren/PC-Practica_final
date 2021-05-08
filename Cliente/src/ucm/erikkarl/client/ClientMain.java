package ucm.erikkarl.client;

import ucm.erikkarl.common.ServerInformation;

import java.io.IOException;
import java.net.Socket;

public class ClientMain {
    public static void main(String[] args) {
        ClientArgumentsParser.parse(args);

        try (var socket = new Socket(ServerInformation.getAddress(), ServerInformation.getPort()))
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
