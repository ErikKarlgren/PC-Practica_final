package ucm.erikkarl.cliente;

import ucm.erikkarl.cliente.oyenteaservidor.OyenteAServidor;
import ucm.erikkarl.common.ServerInformation;
import ucm.erikkarl.common.cliente.Cliente;
import ucm.erikkarl.common.concurrency.MessagesQueue;
import ucm.erikkarl.common.logging.FlexibleHandler;
import ucm.erikkarl.common.logging.SocketReadyLogger;
import ucm.erikkarl.common.mensajes.delcliente.MensajeDelCliente;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CodigoCliente
        implements Cliente {

    private static final Logger LOGGER = SocketReadyLogger.create(CodigoCliente.class.getName());
    private final ServerInformation serverInfo;
    private final MessagesQueue messagesForServer;
    private final CLI cli;
    private final LocalFilesManager filesManager;
    private String username;
    private InetAddress localAddress;
    private Socket socketToServer;
    private volatile boolean connectedToServer = false;

    // private DownloadManager
    // private UploadManager

    public CodigoCliente(ServerInformation serverInfo) {
        this.serverInfo = serverInfo;
        messagesForServer = new MessagesQueue();
        filesManager = new LocalFilesManager();
        cli = new CLI(this, System.in, System.out, filesManager);

        try
        {
            localAddress = InetAddress.getLocalHost();
            socketToServer = new Socket(serverInfo.getAddress(), serverInfo.getPort());
            connectedToServer = true;
        }
        catch (UnknownHostException e)
        {
            LOGGER.log(Level.SEVERE, "Could not set local address", e);
            System.exit(-1);
        }
        catch (IOException e)
        {
            LOGGER.log(Level.SEVERE, "Error during connection to server", e);
            System.exit(-2);
        }
    }

    public static void main(String[] args) {
        try
        {
            FlexibleHandler.setHandlerMode(FlexibleHandler.LoggingHandlerMode.FILE);
            var serverInfo = ClientArgumentsParser.parse(args);
            var cliente = new CodigoCliente(serverInfo);
            cliente.run();
        }
        catch (InterruptedException e)
        {
            LOGGER.log(Level.SEVERE, "Current thread was interrupted", e);
            Thread.currentThread().interrupt();
        }
        catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Error", e);
        }
        finally
        {
            for (var l : Logger.getLogger("").getHandlers())
            {
                l.close();
            }
        }
    }

    public void run() throws InterruptedException {
        var oyenteThread = new Thread(new OyenteAServidor(this, socketToServer, messagesForServer), "oyente");
        var cliThread = new Thread(cli, "command line interface");

        cliThread.start();
        oyenteThread.start();

        cliThread.join();
        oyenteThread.join();

        LOGGER.info("Finished connection to server");
    }

    @Override
    public String uid() {
        return username;
    }

    @Override
    public String ip() {
        return localAddress.getHostAddress();
    }

    @Override
    public String serverIP() {
        return serverInfo.getAddress();
    }

    @Override
    public void mandarMensajeAServidor(MensajeDelCliente msg) {
        messagesForServer.add(msg);
    }

    @Override
    public void confirmarInicioSesion(boolean confirmado) {
        if (!confirmado)
        {
            LOGGER.severe("Could not log in");
            this.connectedToServer = false;
        }
        else
            this.connectedToServer = true;
    }

    @Override
    public void confirmarCierreSesion(boolean confirmado) {
        if (!confirmado)
            throw new IllegalStateException("Could not log out");
        else
            this.connectedToServer = false;
    }

    @Override
    public void mostrarTextoPorConsola(String texto) {
        cli.printInput(texto);
    }

    @Override
    public boolean isConnectedToServer() {
        return connectedToServer;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }
}
