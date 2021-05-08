package ucm.erikkarl.server;

import ucm.erikkarl.common.SocketRunnable;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Clase para que el servidor interactue con un cliente. Usa un {@link Socket}
 * que se cierra automaticamente al finalizar la ejecucion.
 */
public class OyenteCliente
        extends SocketRunnable {

    /**
     * Crea un {@link OyenteCliente} a partir de un {@link Socket}.
     *
     * @param socket {@link Socket} para comunicarse con el cliente.
     */
    protected OyenteCliente(Socket socket) {
        super(socket);
    }

    @Override
    protected void runUsingSocket(Socket socket) throws IOException {
        System.out.println("<" + socket + "> Connected to new client");

        var in = new Scanner(socket.getInputStream(), StandardCharsets.UTF_8);
        var out = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);

        out.println("Welcome to my Minecraft Server! UwU");
        out.println("Now, tell me your name: ");
        var name = in.nextLine();
        System.out.println("<" + socket + "> Client's name is " + name);
        out.println("Welcome " + name + "!");
    }
}
