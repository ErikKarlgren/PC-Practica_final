package ucm.erikkarl.server;

import ucm.erikkarl.common.SocketRunnable;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Clase para que el servidor interactue con un cliente. Usa un {@link Socket}
 * que se cierra automaticamente al finalizar la ejecucion.
 */
public class OyenteServidor
        extends SocketRunnable {

    /**
     * Crea un {@link OyenteServidor} a partir de un {@link Socket}.
     *
     * @param socket {@link Socket} para comunicarse con el cliente.
     */
    protected OyenteServidor(Socket socket) {
        super(socket);
    }

    @Override
    protected void runUsingSocket(Socket socket) throws IOException {
        System.out.println("<" + socket + "> Connected to new client");

        var in = new Scanner(socket.getInputStream(), StandardCharsets.UTF_8);
        var out = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);

        var cwd = System.getProperty("user.dir");
        var filesDirectory = new File(cwd + "//Files");
        var files = filesDirectory.listFiles();

        if (files != null)
        {
            System.out.println("<" + socket + "> Sending information about files...");

            // Mandamos el numero de ficheros al cliente para que sepa cuantas lineas necesita leer
            out.println(files.length);
            System.out.printf("There are %d files%n", files.length);

            for (var f : files)
            {
                out.println(f.getName());
                System.out.printf("\t%s%n", f.getName());
            }

            // Esperamos una señal de confirmacion del cliente para poder cerrar el canal
            var confirmation = in.nextBoolean();
            if (confirmation)
                System.out.println("<" + socket + "> Connection finished with client");
            else
                throw new IOException("Expected a 'true' confirmation signal from client");
        }
        else
            throw new NullPointerException("Could not list files of current working directory ('files' is null)");
    }
}
