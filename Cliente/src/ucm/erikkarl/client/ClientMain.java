package ucm.erikkarl.client;

import java.io.IOException;
import java.net.Socket;

public class ClientMain {
    public static void main(String[] args) {
        var serverInfo = ClientArgumentsParser.parse(args);

        try (var socket = new Socket(serverInfo.getAddress(), serverInfo.getPort()))
        {
            var oyente = new OyenteAServidor(socket);
            oyente.run();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
