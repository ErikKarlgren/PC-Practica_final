package ucm.erikkarl.server.oyenteacliente;

import ucm.erikkarl.common.concurrency.MessagesQueue;
import ucm.erikkarl.common.exceptions.UserOfflineOrDoesNotExistException;
import ucm.erikkarl.common.logging.SocketReadyLogger;
import ucm.erikkarl.common.mensajes.delservidor.MensajeDelServidor;
import ucm.erikkarl.common.server.DatosUsuarios;
import ucm.erikkarl.common.server.EstadoConexion;
import ucm.erikkarl.common.server.SesionServidor;
import ucm.erikkarl.common.server.Usuario;
import ucm.erikkarl.server.Servidor;

import java.net.Socket;
import java.util.Objects;

public class SesionDelCliente
        implements SesionServidor, Runnable
{
    private static final SocketReadyLogger LOGGER = SocketReadyLogger.create(SesionDelCliente.class.getName());
    private final Servidor servidor;
    private final MessagesQueue queue;
    private final Socket socket;
    private volatile boolean closeConnection = false;
    private Usuario usuarioActual = null;

    public SesionDelCliente(Servidor servidor, Socket socket) {
        this.servidor = servidor;
        this.socket = socket;
        this.queue = new MessagesQueue();
        LOGGER.setSocket(socket);
    }

    @Override
    public void run() {
        var oyenteACliente = new OyenteACliente(this, socket, queue);
        oyenteACliente.run();
        LOGGER.info("Finished connection to client");
    }

    @Override
    public String ip() {
        return servidor.ip();
    }

    @Override
    public int port() {
        return servidor.localPort();
    }

    @Override
    public DatosUsuarios datosUsuarios() {
        return servidor.datosUsuarios();
    }

    @Override
    public boolean iniciarSesion(Usuario usuario) {
        boolean exito;
        var registeredUser = servidor.datosUsuarios().get(usuario.uid());

        if (registeredUser.isPresent() && registeredUser.get().estadoConexion() == EstadoConexion.ONLINE)
        {
            LOGGER.warning("Attempted login from a currently online user");
            exito = false;
        }
        else if (usuarioActual != null)
        {
            LOGGER.warning("Attempted login when a user was already online");
            exito = false;
        }
        else
        {
            usuario.setEstadoConexion(EstadoConexion.ONLINE);
            usuarioActual = usuario;
            servidor.datosUsuarios().put(usuarioActual);
            if (!servidor.datosUsuarios().setSesionDelUsuario(usuarioActual.uid(), this))
            {
                LOGGER.severe("Could not set user's session when logging in");
                exito = false;
            }
            else
            {
                LOGGER.info("New user's session was set");
                exito = true;
            }
        }
        return exito;
    }

    @Override
    public boolean cerrarSesion() {
        boolean exito;

        var usu = servidor.datosUsuarios().get(usuarioActual.uid());
        if (usu.isEmpty())
        {
            LOGGER.warning("Current user was not found on the server");
            exito = false;
        }
        else if (usu.get().estadoConexion() == EstadoConexion.OFFLINE)
        {
            LOGGER.warning("Trying to log out an offline user");
            exito = false;
        }
        else
        {
            usuarioActual.setEstadoConexion(EstadoConexion.OFFLINE);
            if (!servidor.datosUsuarios().setSesionDelUsuario(usuarioActual.uid(), null))
            {
                LOGGER.severe("Could not change user's session");
                exito = false;
            }
            else
            {
                LOGGER.info("Updating user's session was succesful");
                exito = true;
            }
            servidor.datosUsuarios().put(usuarioActual);
        }
        return exito;
    }

    @Override
    public void cerrarConexion() {
        closeConnection = true;
        LOGGER.info("Closing connection");
    }

    @Override
    public boolean conexionAbierta() {
        return !closeConnection;
    }

    @Override
    public Usuario usuarioActual() {
        return usuarioActual;
    }

    @Override
    public void mandarMensajeACliente(MensajeDelServidor msg, String username) throws UserOfflineOrDoesNotExistException
    {
        var sesion = servidor.datosUsuarios().sesionDelUsuario(username);
        if (sesion.isEmpty())
            throw new UserOfflineOrDoesNotExistException();
        else
        {
            LOGGER.info(() -> "Sending message to a diferent user: " + username + " (" + msg + ")");
            sesion.get().mandarMensajeACliente(Objects.requireNonNull(msg));
        }
    }

    @Override
    public void mandarMensajeACliente(MensajeDelServidor msg) {
        LOGGER.info(() -> "Sending message to current user: " + usuarioActual.uid() + " (" + msg + ")");
        queue.add(msg);
    }
}
