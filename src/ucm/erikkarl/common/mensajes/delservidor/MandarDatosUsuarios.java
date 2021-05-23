package ucm.erikkarl.common.mensajes.delservidor;

import ucm.erikkarl.common.cliente.Cliente;
import ucm.erikkarl.common.logging.SocketReadyLogger;
import ucm.erikkarl.common.mensajes.Mensaje;
import ucm.erikkarl.common.mensajes.TipoMensaje;
import ucm.erikkarl.common.server.Usuario;

import java.util.Objects;
import java.util.TreeSet;
import java.util.logging.Logger;

/**
 * {@link Mensaje} para mandarle al cliente una instantanea de los datos de los usuarios
 * registrados en el servidor.
 */
public final class MandarDatosUsuarios
        extends MensajeDelServidor
{
    private static final Logger LOGGER = SocketReadyLogger.create(MandarDatosUsuarios.class.getName());
    private final TreeSet<Usuario> usuarios;
    private final boolean exito;

    public MandarDatosUsuarios(TreeSet<Usuario> users, final boolean exito, String origin, String destiny) {
        super(TipoMensaje.MANDAR_LISTA_USUARIOS, origin, destiny);
        this.exito = exito;

        if (exito)
            this.usuarios = Objects.requireNonNull(users);
        else
            this.usuarios = null;
    }

    @Override
    public void getProcessedBy(Cliente cliente) {
        if (exito)
            printUsersInfo(cliente);
        else
            printError(cliente);
    }

    private void printUsersInfo(final Cliente cliente) {
        var sb = new StringBuilder();
        for (var usu : Objects.requireNonNull(usuarios))
        {
            var header = String.format("%s [%s] - %s", usu.uid(), usu.ip(), usu.estadoConexion());
            var filenames = usu.ficherosDisponibles().toString();
            var line = String.format("%s: %s%n", header, filenames);
            sb.append(line);
        }
        cliente.mostrarTextoPorConsola(sb.toString());
        LOGGER.fine("Users info was printed");
    }

    private void printError(final Cliente cliente) {
        var error = "Could not retrieve users data";
        cliente.mostrarTextoPorConsola(error);
        LOGGER.warning(error);
    }
}
