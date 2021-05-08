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
public class OyenteCliente
        extends SocketRunnable {

    /**
     * Crea un {@link OyenteCliente} a partir de un {@link Socket}.
     *
     * @param socket {@link Socket} para oir al servidor.
     */
    public OyenteCliente(Socket socket) {
        super(socket);
    }

    @Override
    protected void runUsingSocket(Socket socket) throws IOException {
        var stdinScanner = new Scanner(System.in, StandardCharsets.UTF_8);
        var in = new Scanner(socket.getInputStream(), StandardCharsets.UTF_8);
        var output = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);

        // Necesitamos saber cuantos ficheros hay disponibles en el servidor
        int numFiles = in.nextInt();
        // El servidor manda los nombres de los ficheros en las siguientes lineas
        // y no habiamos acabado la linea con in.nextInt()
        in.nextLine();
        System.out.printf("There are %d files on the server%n", numFiles);

        for (int i = 0; i < numFiles; i++)
        {
            var name = in.nextLine();
            System.out.println('\t' + name);
        }
        // Mandamos una señal al servidor para que pueda cerrar ya el canal
        output.println(true);
        System.out.println("Those were all the files");
    }
}
