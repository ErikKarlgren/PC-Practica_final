package ucm.erikkarl.server;

import ucm.erikkarl.common.ServerInformation;
import ucm.erikkarl.common.logging.FlexibleHandler;
import ucm.erikkarl.common.logging.SocketReadyLogger;
import ucm.erikkarl.common.server.DatosUsuarios;
import ucm.erikkarl.server.datosusuarios.MapaConcurrenteUsuarios;
import ucm.erikkarl.server.oyenteacliente.SesionDelCliente;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {
    private static final Logger LOGGER = SocketReadyLogger.create(Servidor.class.getName());
    private final DatosUsuarios datosUsuarios;
    private String hostAddress;
    private int localPort;

    private Servidor() {
        datosUsuarios = new MapaConcurrenteUsuarios();

        try
        {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException e)
        {
            LOGGER.severe("Host address attribute could not be set");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        FlexibleHandler.setHandlerMode(FlexibleHandler.LoggingHandlerMode.CONSOLE);
        ServerArgumentsParser.parse(args);
        var servidor = new Servidor();
        servidor.run();
    }

    public String ip() {
        return hostAddress;
    }

    public int localPort() {
        return localPort;
    }

    public DatosUsuarios datosUsuarios() {
        return datosUsuarios;
    }

    private void run() {
        try (var serverSocket = new ServerSocket(ServerInformation.getDefaultPort()))
        {
            localPort = serverSocket.getLocalPort();
            LOGGER.info(() -> "Address: " + hostAddress + ", Port: " + localPort);

            while (!Thread.currentThread().isInterrupted())
            {
                var socket = serverSocket.accept();
                var nuevaSesion = new SesionDelCliente(this, socket);
                new Thread(nuevaSesion, "sesion-" + socket).start();
            }
        }
        catch (IOException e)
        {
            LOGGER.log(Level.SEVERE, "Error during server execution", e);
        }
        finally
        {
            LOGGER.info("Server has shut down");
        }
    }
}
