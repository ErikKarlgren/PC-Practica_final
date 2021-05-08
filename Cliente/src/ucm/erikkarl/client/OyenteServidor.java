package ucm.erikkarl.client;

import ucm.erikkarl.common.SocketRunnable;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Clase para que el cliente interactue con el servidor. Usa un {@link Socket}
 * que se cierra automaticamente al finalizar la ejecucion.
 */
public class OyenteServidor
        extends SocketRunnable {

    /**
     * Crea un {@link OyenteServidor} a partir de un {@link Socket}.
     *
     * @param socket {@link Socket} para oir al servidor.
     */
    public OyenteServidor(Socket socket) {
        super(socket);
    }

    @Override
    protected void runUsingSocket(Socket socket) throws IOException {
        var stdinScanner = new Scanner(System.in, StandardCharsets.UTF_8);
        var in = new Scanner(socket.getInputStream(), StandardCharsets.UTF_8);
        var output = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);

        System.out.println(in.nextLine());
        System.out.println(in.nextLine());
        output.println(stdinScanner.nextLine());
        System.out.println(in.nextLine());
    }
}
