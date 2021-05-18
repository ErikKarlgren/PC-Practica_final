package ucm.erikkarl.common.mensajes.delservidor;

import ucm.erikkarl.common.cliente.Cliente;
import ucm.erikkarl.common.mensajes.Mensaje;
import ucm.erikkarl.common.mensajes.TipoMensaje;
import ucm.erikkarl.common.server.Usuario;

import java.util.TreeSet;

/**
 * {@link Mensaje} para mandarle al cliente una instantanea de los datos de los usuarios
 * registrados en el servidor.
 */
public final class MandarDatosUsuarios
        extends MensajeDelServidor {
    private final TreeSet<Usuario> users;

    public MandarDatosUsuarios(TreeSet<Usuario> users, String origin, String destiny) {
        super(TipoMensaje.MANDAR_LISTA_USUARIOS, origin, destiny);
        this.users = users;
    }

    @Override
    public void getProcessedBy(Cliente cliente) {
        cliente.mostrarDatosUsuarios(users);
    }
}
