package ucm.erikkarl.cliente;

import ucm.erikkarl.common.logging.SocketReadyLogger;
import ucm.erikkarl.common.mensajes.delcliente.PeticionCierreSesion;
import ucm.erikkarl.common.mensajes.delcliente.PeticionInicioSesion;
import ucm.erikkarl.common.server.EstadoConexion;
import ucm.erikkarl.common.server.Usuario;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CLI
        implements Runnable {

    private static final Logger LOGGER = SocketReadyLogger.create(CLI.class.getName());

    private final Scanner in;
    private final PrintStream out;
    private final Semaphore waiting;
    private final CodigoCliente cliente;
    private final LocalFilesManager filesManager;

    public CLI(CodigoCliente cliente, InputStream in, OutputStream out, LocalFilesManager filesManager) {
        this.in = new Scanner(in);
        this.out = new PrintStream(out);
        this.cliente = cliente;
        this.filesManager = filesManager;
        waiting = new Semaphore(0);
    }

    @Override
    public void run() {
        try
        {
            login();
            out.println("Connected to server");
            while (cliente.connectionToServerHasNotBeenClosed())
            {
                out.print("> ");
                switch (in.nextLine())
                {
                    // TODO: añadir opciones reales
                    case "hi", "hello" -> out.println("hello dude");
                    case "exit", "logout" -> out.println("nah");
                    default -> out.println(help());
                }
            }
            out.println("Disconnected from server");
            LOGGER.info("Disconnected from server");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            out.println("Could not connect to server");
            LOGGER.log(Level.SEVERE, "Could not connect to server", e);
        }
    }

    /**
     * Makes the cli wait until an input is received through {@link CLI#printInput(String)}.
     */
    private void waitForInput() {
        LOGGER.info("Waiting for input");
        waiting.acquireUninterruptibly();
    }

    /**
     * Sends a string to be printed to the {@link CLI} and wakes it up.
     *
     * @param input String to be printed
     */
    public void printInput(String input) {
        out.println(input);
        LOGGER.info("Woke up: input has been received and printed");
        waiting.release();
    }

    /**
     * Asks for a username and tries to log into the server.
     */
    private void login() throws IOException {
        var name = askForUsername();
        cliente.setUsername(name);

        List<String> filenames = filesManager.getDownloadableFilesNames();

        var usuario = new Usuario(name, cliente.ip(), EstadoConexion.OFFLINE);
        usuario.setFicherosDisponibles(filenames);

        var msg = new PeticionInicioSesion(usuario, cliente.ip(), cliente.serverIP());
        cliente.mandarMensajeAServidor(msg);
        waitForInput();
        LOGGER.info("Login was succesful");
    }

    /**
     * Tries to log out from the server.
     *
     * @return {@code true} if client logs out succesfully, {@code false} otherwise.
     */
    private boolean logout() {
        var msg = new PeticionCierreSesion(cliente.ip(), cliente.serverIP());
        cliente.mandarMensajeAServidor(msg);
        waitForInput();
        return !cliente.connectionToServerHasNotBeenClosed();
    }

    /**
     * Asks for user's name
     *
     * @return user's name
     */
    private String askForUsername() {
        out.print("Username: ");
        return in.nextLine();
    }

    /**
     * @return {@link String} with help message
     */
    private String help() {
        return """
                exit: exits program
                help: shows this menu
                 """;
    }
}
