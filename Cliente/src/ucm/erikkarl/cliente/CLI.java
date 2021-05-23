package ucm.erikkarl.cliente;

import ucm.erikkarl.common.cliente.Cliente;
import ucm.erikkarl.common.exceptions.UnsuccesfulLoginException;
import ucm.erikkarl.common.logging.SocketReadyLogger;
import ucm.erikkarl.common.mensajes.delcliente.PeticionCierreSesion;
import ucm.erikkarl.common.mensajes.delcliente.PeticionDatosUsuarios;
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
        implements Runnable
{
    private static final Logger LOGGER = SocketReadyLogger.create(CLI.class.getName());
    private final Scanner in;
    private final PrintStream out;
    private final Semaphore waiting;
    private final Cliente cliente;
    private final LocalFilesManager filesManager;

    public CLI(Cliente cliente, InputStream in, OutputStream out, LocalFilesManager filesManager) {
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
            while (cliente.isConnectedToServer())
                readAndExecute();

            out.println("Disconnected from server");
            LOGGER.info("Disconnected from server");
        }
        catch (IOException e)
        {
            out.println("Could not read available files");
            LOGGER.log(Level.SEVERE, "Could not read available files", e);
        }
        catch (UnsuccesfulLoginException e)
        {
            out.println("Could not log into the server");
            LOGGER.log(Level.SEVERE, "Could not log into the server", e);
        }
    }

    private void readAndExecute() {
        out.print("> ");
        switch (in.nextLine().trim())
        {
            case "hi", "hello" -> out.println("hello dude");
            case "users" -> askForUsersData();
            case "exit", "logout" -> logout();
            default -> out.println(help());
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
    private void login() throws IOException, UnsuccesfulLoginException {
        var name = askForUsername();
        cliente.setUsername(name);

        List<String> filenames = filesManager.getDownloadableFilesNames();

        var usuario = new Usuario(name, cliente.ip(), EstadoConexion.OFFLINE);
        usuario.setFicherosDisponibles(filenames);

        var msg = new PeticionInicioSesion(usuario, cliente.ip(), cliente.serverIP());
        cliente.mandarMensajeAServidor(msg);
        waitForInput();

        if (!cliente.isConnectedToServer())
            throw new UnsuccesfulLoginException("Login was unsuccesful");
    }

    /**
     * Tries to log out from the server.
     */
    private void logout() {
        var msg = new PeticionCierreSesion(cliente.ip(), cliente.serverIP());
        cliente.mandarMensajeAServidor(msg);
        waitForInput();

        if (cliente.isConnectedToServer())
            LOGGER.severe("Could not log out succesfully");
    }

    /**
     * Tries to get registered users data from server.
     */
    private void askForUsersData() {
        var msg = new PeticionDatosUsuarios(cliente.ip(), cliente.serverIP());
        cliente.mandarMensajeAServidor(msg);
        waitForInput();
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
                    hi, hello: greetings, sir!
                    users: show users data
                    help: shows this menu
                    exit, logout: exits program
                """;
    }
}
