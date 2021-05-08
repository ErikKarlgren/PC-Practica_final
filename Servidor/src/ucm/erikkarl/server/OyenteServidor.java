package ucm.erikkarl.server;

import ucm.erikkarl.common.SocketRunnable;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase para que el servidor interactue con un cliente. Usa un {@link Socket}
 * que se cierra automaticamente al finalizar la ejecucion.
 */
public class OyenteServidor
        extends SocketRunnable {

    private static Logger LOGGER = Logger.getLogger(OyenteServidor.class.getName());

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
        LOGGER.info("Connection accepted from " + socket.getInetAddress().getHostAddress());

        var in = new Scanner(socket.getInputStream(), StandardCharsets.UTF_8);
        var out = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);

        var cwd = System.getProperty("user.dir");
        var filesDirectory = new File(cwd + "//Files");
        var files = filesDirectory.listFiles();

        if (files != null)
        {
            LOGGER.info("Sending information about files to " + socket.getInetAddress().getHostAddress());

            // Mandamos el numero de ficheros al cliente para que sepa cuantas lineas necesita leer
            out.println(files.length);

            // Pasamos como argumento un Supplier<String> para que el mensaje solo se construya si es necesario
            LOGGER.fine(() -> "There are " + files.length + " files");

            for (var f : files)
                out.println(f.getName());
            LOGGER.fine(() -> "Files: " + Arrays.toString(files));

            // Esperamos una señal de confirmacion del cliente para poder cerrar el canal
            var confirmation = in.nextBoolean();
            if (confirmation)
                LOGGER.info("Connection finished with client");
            else
                throw new IOException("Expected a 'true' confirmation signal from client");
        }
        else
            throw new NullPointerException("Could not list files of current working directory ('files' is null)");
    }
}
