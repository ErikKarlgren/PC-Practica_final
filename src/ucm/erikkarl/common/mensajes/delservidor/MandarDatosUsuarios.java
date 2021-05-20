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
    private final TreeSet<Usuario> usuarios;

    public MandarDatosUsuarios(TreeSet<Usuario> users, String origin, String destiny) {
        super(TipoMensaje.MANDAR_LISTA_USUARIOS, origin, destiny);
        this.usuarios = users;
    }

    @Override
    public void getProcessedBy(Cliente cliente) {
        var sb = new StringBuilder();

        for (var usu : usuarios)
        {
            var header = String.format("%s [%s] - %s", usu.uid(), usu.ip(), usu.estadoConexion());
            var filenames = usu.ficherosDisponibles().toString();
            var line = String.format("%s: %s%n", header, filenames);
            sb.append(line);
        }
        cliente.mostrarTextoPorConsola(sb.toString());
    }
}
